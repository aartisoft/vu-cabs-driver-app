package technians.com.vucabsdriver.View.MainView.Fragments.Passes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.rest.ApiInterface;

public class GatewayChooseActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    private int Amount, Rides;
    ProgressDialog progressDialog;
    String DriverId;
    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gateway_choose);
        progressDialog = Constants.showProgressDialog(GatewayChooseActivity.this);
        order_id = String.valueOf(System.currentTimeMillis());
        DriverId = String.valueOf(new SessionManager(GatewayChooseActivity.this).getDriverId());

        Amount = 1;
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
                break;
            }
        }
    }

    private void startTransaction(final String order_id) {
        progressDialog.show();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.vucabs.com/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<String> call = apiService.paytmchecksum("ShriHa03311053236907", order_id,
                DriverId, "Retail", "WAP",
                String.valueOf(Amount), "APPSTAGING",
                "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + order_id + "");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull retrofit2.Response<String> response) {
                progressDialog.dismiss();
                String checksumhash = response.body().toString();
                initializeTransaction(checksumhash, order_id);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.v("PayTmActivity", "Error: " + t.getMessage());
                progressDialog.dismiss();
                String error = new NetworkError(t).getAppErrorMessage();
                Toast.makeText(GatewayChooseActivity.this, error, Toast.LENGTH_SHORT).show();
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
        paramMap.put("MID", "ShriHa03311053236907");
        paramMap.put("ORDER_ID", orderid);
        paramMap.put("CUST_ID", DriverId);
        paramMap.put("INDUSTRY_TYPE_ID", "Retail");
        paramMap.put("CHANNEL_ID", "WAP");
        paramMap.put("TXN_AMOUNT", String.valueOf(Amount));
        paramMap.put("WEBSITE", "APPSTAGING");
        paramMap.put("CALLBACK_URL", "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderid + "");
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
//					@Override
//					public void onTransactionFailure(String inErrorMessage,
//							Bundle inResponse) {
//						// This method gets called if transaction failed. //
//						// Here in this case transaction is completed, but with
//						// a failure. // Error Message describes the reason for
//						// failure. // Response bundle contains the merchant
//						// response parameters.
//						Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
//						Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
//					}
//                    @Override
//                    public void onTransactionSuccess(Bundle inResponse) {
//                        // After successful transaction this method gets called.
//                        // // Response bundle contains the merchant response
//                        // parameters.
//                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
//                        Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
//                    }

                    @Override
                    public void onTransactionResponse(Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction is successful " + inResponse);
                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
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
                        Toast.makeText(GatewayChooseActivity.this, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                        Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    }

                });
    }
}
