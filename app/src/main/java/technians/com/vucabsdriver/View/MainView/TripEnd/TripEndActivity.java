package technians.com.vucabsdriver.View.MainView.TripEnd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import technians.com.vucabsdriver.Model.PendingRequest.BookingData;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.RealmController1;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.MainView.ContainerActivity.NavigationActivity;

import static technians.com.vucabsdriver.Utilities.Constants.FormatAmount;

public class TripEndActivity extends AppCompatActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_end);
        RealmController1 realmController1 = new RealmController1(this);
        realm = Realm.getInstance(realmController1.initializeDB());
        Toolbar toolbar = findViewById(R.id.activity_tripend_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(getString(R.string.billend));
        TextView mTextViewCustName = findViewById(R.id.activity_tripend_custname);
        TextView mTextViewAmount = findViewById(R.id.activity_tripend_amount);
        TextView mTextViewPaymentMode = findViewById(R.id.activity_tripend_paymentmode);
        TextView mTextViewTotalPayable = findViewById(R.id.activity_tripend_totalpayable);
        TextView mTextViewToll = findViewById(R.id.activity_tripend_taxes);
        TextView mTextViewTax = findViewById(R.id.activity_tripend_tax);
        TextView mTextViewBaseamount = findViewById(R.id.activity_tripend_baseamount);
        Button mBtnContinue = findViewById(R.id.activity_enter_otp_btn_continue);

        int ID = getIntent().getIntExtra("ID", 0);
        BookingData bookingData = realm.where(BookingData.class).equalTo("id", ID).findFirst();

        mTextViewCustName.setText(bookingData.getCustomer_name());
        mTextViewAmount.setText(FormatAmount(bookingData.getTotal_amount()));
        mTextViewTotalPayable.setText(FormatAmount(bookingData.getTotal_amount()));
        mTextViewToll.setText(FormatAmount(bookingData.getToll()));
        mTextViewTax.setText(FormatAmount(bookingData.getGst_amount()));
        mTextViewBaseamount.setText(FormatAmount(bookingData.getRide_amount()));
        String upperString = bookingData.getPayment_type().substring(0, 1).toUpperCase() + bookingData.getPayment_type().substring(1);
        mTextViewPaymentMode.setText(upperString);

        new SessionManager(TripEndActivity.this).setDrivingActive(false);
        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TripEndActivity.this, NavigationActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }
}
