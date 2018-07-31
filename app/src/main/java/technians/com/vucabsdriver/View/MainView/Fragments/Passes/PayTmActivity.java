package technians.com.vucabsdriver.View.MainView.Fragments.Passes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import technians.com.vucabsdriver.Model.Documents.DocumentResponce;
import technians.com.vucabsdriver.Model.Documents.DocumentsList;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

public class PayTmActivity extends AppCompatActivity {
    private int Amount, Rides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_tm);
//        progressDialog = Constants.showProgressDialog(PayTmActivity.this);
//        Amount = getIntent().getIntExtra("Amount", 0);
//        Rides = getIntent().getIntExtra("Rides_qty", 0);
//        order_id = String.valueOf(System.currentTimeMillis());
//        DriverId = String.valueOf(new SessionManager(PayTmActivity.this).getDriverId());
//        startTransaction(order_id);
    }


}
