package technians.com.vucabsdriver.View.MainView.Fragments.Passes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Call;
import okhttp3.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import technians.com.vucabsdriver.AppController;
import technians.com.vucabsdriver.Model.PassesRecharge.PassesRechargeResponce;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.PayUMoneyIntegrate.PayUMoneyActivity;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.Utilities.SessionManager;
import okhttp3.Response;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

public class GatewayChooseActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    private int Amount, Rides;
    ProgressDialog progressDialog;
    String DriverId;
    String order_id;
    private AppController appController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway_choose);
        progressDialog = Constants.showProgressDialog(GatewayChooseActivity.this);
        appController = (AppController)getApplicationContext();
        DriverId = String.valueOf(new SessionManager(GatewayChooseActivity.this).getDriverId());
        order_id = "VUCABS"+DriverId+String.valueOf(System.currentTimeMillis());
        Amount = getIntent().getIntExtra("Amount", 0);
        Rides = getIntent().getIntExtra("Rides_qty", 0);
        toolbar = findViewById(R.id.activity_gatewaychoosetoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Payment");
        TextView paymentButton_paytm = findViewById(R.id.paymentButton_paytm);
        TextView paymentButton_paytU = findViewById(R.id.paymentButton_payU);
        paymentButton_paytm.setOnClickListener(this);
        paymentButton_paytU.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.paymentButton_paytm: {
                startTransaction(order_id);
                break;
            }
            case R.id.paymentButton_payU: {
                startActivityForResult(new Intent(GatewayChooseActivity.this, PayUMoneyActivity.class)
                        .putExtra("Amount", Amount)
                        .putExtra("Rides_qty", Rides),6);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    private void startTransaction(final String order_id) {
        progressDialog.show();
        RequestBody body = new FormBody.Builder().
                add( "MID" , "ShriHH48818866225445").
                add( "ORDER_ID" , order_id).
                add( "CUST_ID" , DriverId).
                add( "INDUSTRY_TYPE_ID" , "Retail109").
                add( "CHANNEL_ID" , "WAP").
                add( "TXN_AMOUNT" , String.valueOf(Amount)).
                add( "WEBSITE" , "ShriHHWAP").
                add( "CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+order_id).
                build();

        final Request request = new Request.Builder().
                url("https://www.vucabs.com/api/paytm-genrate-checksum").
                post(body).
                build();

        appController.getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       initializeTransaction(json,order_id);
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeTransaction(String checksumhash, String orderid) {
        PaytmPGService Service = PaytmPGService.getProductionService();

        // these are mandatory parameters
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("MID", "ShriHH48818866225445");
        paramMap.put("ORDER_ID", orderid);
        paramMap.put("CUST_ID", DriverId);
        paramMap.put("INDUSTRY_TYPE_ID", "Retail109");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", String.valueOf(Amount));
        paramMap.put("WEBSITE", "ShriHHWAP");
        paramMap.put("CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=" + orderid + "");
        paramMap.put("CHECKSUMHASH", checksumhash);

        PaytmOrder Order = new PaytmOrder(paramMap);

		/*PaytmMerchant Merchant = new PaytmMerchant(
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*/

        Service.initialize(Order, null);

        Service.startPaymentTransaction(GatewayChooseActivity.this, true, true,
                new PaytmPaymentTransactionCallback() {
                    @Override
                    public void someUIErrorOccurred(String inErrorMessage) {
                        // Some UI Error Occurred in Payment Gateway Activity.
                        // // This may be due to initialization of views in
                        // Payment Gateway Activity or may be due to //
                        // initialization of webview. // Error Message details
                        // the error occurred.
                    }


                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                        if (inResponse.getString("STATUS").equalsIgnoreCase("TXN_SUCCESS")){
                            AddPayment(new SessionManager(GatewayChooseActivity.this).getDriverName(),Rides,Amount);
//                            final MaterialStyledDialog.Builder dialogHeader_1 = new MaterialStyledDialog.Builder(GatewayChooseActivity.this)
//                                    .setIcon(R.drawable.ic_check_circle_coloraccent_50_48dp)
//                                    .withDialogAnimation(true)
//                                    .withIconAnimation(true)
//                                    .setHeaderColorInt(Color.BLACK)
//                                    .setTitle(getString(R.string.rechrgesuccess))
//                                    .setDescription(getString(R.string.rechargedescription))
//                                    .setPositiveText(getString(R.string.Continue))
//                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
//                                        @Override
//                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                            dialog.dismiss();
//                                            Intent intent = new Intent();
//                                            setResult(RESULT_OK, intent);
//                                            finish();
//                                        }
//                                    });
//                            dialogHeader_1.show();
                        }else {
                            new MaterialStyledDialog.Builder(GatewayChooseActivity.this)
                                    .setIcon(R.drawable.ic_cancel_red_a700_24dp)
                                    .withDialogAnimation(true)
                                    .withIconAnimation(true)
                                    .setHeaderColorInt(Color.BLACK)
                                    .setTitle(getString(R.string.rechrgefail))
                                    .setDescription(getString(R.string.rechargefaildescription))
                                    .setPositiveText(getString(R.string.btn_ok))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                            Intent intent = new Intent();
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    })
                                    .show();
                        }


                    }

                    @Override
                    public void networkNotAvailable() { // If network is not
                        // available, then this
                        // method gets called.
                    }

                    @Override
                    public void clientAuthenticationFailed(String inErrorMessage) {
                        // This method gets called if client authentication
                        // failed. // Failure may be due to following reasons //
                        // 1. Server error or downtime. // 2. Server unable to
                        // generate checksum or checksum response is not in
                        // proper format. // 3. Server failed to authenticate
                        // that client. That is value of payt_STATUS is 2. //
                        // Error Message describes the reason for failure.
                    }

                    @Override
                    public void onErrorLoadingWebPage(int iniErrorCode,
                                                      String inErrorMessage, String inFailingUrl) {

                    }

                    // had to be added: NOTE
                    @Override
                    public void onBackPressedCancelTransaction() {
                        Toast.makeText(GatewayChooseActivity.this,"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });

    }

    private void AddPayment(final String Name, final int RideQuantity, final int Amount) {
        try {
            progressDialog.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            retrofit2.Call<PassesRechargeResponce> call = apiService.getRechargeDetails(new SessionManager(GatewayChooseActivity.this).getToken()
                    , String.valueOf(DriverId), Name, String.valueOf(Amount), String.valueOf(RideQuantity));
            call.enqueue(new retrofit2.Callback<PassesRechargeResponce>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<PassesRechargeResponce> call, @NonNull retrofit2.Response<PassesRechargeResponce> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            final MaterialStyledDialog.Builder dialogHeader_1 = new MaterialStyledDialog.Builder(GatewayChooseActivity.this)
                                    .setIcon(R.drawable.ic_check_circle_coloraccent_50_48dp)
                                    .withDialogAnimation(true)
                                    .withIconAnimation(true)
                                    .setHeaderColorInt(Color.BLACK)
                                    .setTitle(getString(R.string.rechrgesuccess))
                                    .setDescription(getString(R.string.rechargedescription))
                                    .setPositiveText(getString(R.string.Continue))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                            Intent intent = new Intent();
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    });
                            dialogHeader_1.show();
                        } else {
                            new MaterialStyledDialog.Builder(GatewayChooseActivity.this)
                                    .setIcon(R.drawable.ic_cancel_red_a700_24dp)
                                    .withDialogAnimation(true)
                                    .withIconAnimation(true)
                                    .setHeaderColorInt(Color.BLACK)
                                    .setTitle(getString(R.string.rechrgefail))
                                    .setDescription(getString(R.string.rechargefaildescription))
                                    .setPositiveText(getString(R.string.btn_ok))
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                            Intent intent = new Intent();
                                            setResult(RESULT_OK, intent);
                                            finish();
                                        }
                                    })
                                    .show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<PassesRechargeResponce> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    String error = new NetworkError(t).getAppErrorMessage();
                    Toast.makeText(GatewayChooseActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }

    }

}
