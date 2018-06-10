package technians.com.vucabsdriver.View.MainView.TripEnd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.realm.Realm;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.View.MainView.ContainerActivity.NavigationActivity;
import technians.com.vucabsdriver.model.PendingRequest.BookingData;

import static technians.com.vucabsdriver.Utilities.Constants.FormatAmount;

public class TripEndActivity extends AppCompatActivity {
    TextView mTextViewCustName,mTextViewAmount, mTextViewPaymentMode, mTextViewTotalPayable, mTextViewToll, mTextViewBaseamount, mTextViewTax;

    Realm realm;
    Button mBtnContinue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_end);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        Toolbar toolbar = findViewById(R.id.activity_tripend_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(getString(R.string.billend));
        mTextViewCustName = findViewById(R.id.activity_tripend_custname);
        mTextViewAmount = findViewById(R.id.activity_tripend_amount);
        mTextViewPaymentMode = findViewById(R.id.activity_tripend_paymentmode);
        mTextViewTotalPayable = findViewById(R.id.activity_tripend_totalpayable);
        mTextViewToll = findViewById(R.id.activity_tripend_taxes);
        mTextViewTax = findViewById(R.id.activity_tripend_tax);
        mTextViewBaseamount = findViewById(R.id.activity_tripend_baseamount);
        mBtnContinue = findViewById(R.id.activity_enter_otp_btn_continue);

        int ID = getIntent().getIntExtra("ID",0);
        BookingData bookingData = realm.where(BookingData.class).equalTo("id",ID).findFirst();

        mTextViewCustName.setText(bookingData.getCustomer_name());
        mTextViewAmount.setText(FormatAmount(bookingData.getTotal_amount()));
        mTextViewTotalPayable.setText(FormatAmount(bookingData.getTotal_amount()));
        mTextViewToll.setText(FormatAmount(bookingData.getToll()));
        mTextViewTax.setText(FormatAmount(bookingData.getGst_amount()));
        mTextViewBaseamount.setText(FormatAmount(bookingData.getRide_amount()));
        String upperString = bookingData.getPayment_type().substring(0, 1).toUpperCase() + bookingData.getPayment_type().substring(1);
        mTextViewPaymentMode.setText(upperString);

        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TripEndActivity.this, NavigationActivity.class));
                finish();
            }
        });
    }
}
