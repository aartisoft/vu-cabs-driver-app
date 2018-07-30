package technians.com.vucabsdriver.View.MainView.BookingOTP;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.RealmController1;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.MainView.AssingedBooking.BookingAssingedActivity;

public class OTPBookingActivity extends AppCompatActivity implements OTPBookingMVPView, View.OnClickListener {
    private EditText mOtpOneField, mOtpTwoField, mOtpThreeField, mOtpFourField, mCurrentlyFocusedEditText;
    private Button mBtnVerifyOTP;
    private ProgressDialog progressDialog;
    private OTPBookingPresenter presenter;
    private Realm realm;
    private int ID;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpbooking);
        sessionManager = new SessionManager(this);
        ID = getIntent().getIntExtra("ID",0);
        RealmController1 realmController1 = new RealmController1(this);
        realm= Realm.getInstance(realmController1.initializeDB());
        progressDialog = Constants.showProgressDialog(this);
        mOtpOneField = findViewById(R.id.bookingotp_one_edit_text);
        mOtpTwoField = findViewById(R.id.bookingotp_two_edit_text);
        mOtpThreeField = findViewById(R.id.bookingotp_three_edit_text);
        mOtpFourField = findViewById(R.id.bookingotp_four_edit_text);
        mBtnVerifyOTP = findViewById(R.id.activity_booking_otp_btn_verifyotp);
        mBtnVerifyOTP.setOnClickListener(this);

        presenter = new OTPBookingPresenter();
        presenter.attachView(this);

        Toolbar toolbar = findViewById(R.id.otpbooking_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(getString(R.string.bookingotp));
        setFocusListener();
        setOnTextChangeListener();
    }

    private void setFocusListener() {
        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mCurrentlyFocusedEditText = (EditText) v;
                mCurrentlyFocusedEditText.setSelection(mCurrentlyFocusedEditText.getText().length());
            }
        };
        mOtpOneField.setOnFocusChangeListener(onFocusChangeListener);
        mOtpTwoField.setOnFocusChangeListener(onFocusChangeListener);
        mOtpThreeField.setOnFocusChangeListener(onFocusChangeListener);
        mOtpFourField.setOnFocusChangeListener(onFocusChangeListener);
    }

    private void setOnTextChangeListener() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mCurrentlyFocusedEditText != null) {
                    if (mCurrentlyFocusedEditText.getText().length() >= 1
                            && mCurrentlyFocusedEditText != mOtpFourField) {
                        mCurrentlyFocusedEditText.focusSearch(View.FOCUS_RIGHT).requestFocus();
                    } else if (mCurrentlyFocusedEditText.getText().length() >= 1
                            && mCurrentlyFocusedEditText == mOtpFourField && getOTP().length() == 4) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        }
                    } else {
                        String currentValue = mCurrentlyFocusedEditText.getText().toString();
                        if (currentValue.length() <= 0 && mCurrentlyFocusedEditText.getSelectionStart() <= 0) {
                            mCurrentlyFocusedEditText.focusSearch(View.FOCUS_LEFT).requestFocus();
                        }
                    }
                }
            }
        };
        mOtpOneField.addTextChangedListener(textWatcher);
        mOtpTwoField.addTextChangedListener(textWatcher);
        mOtpThreeField.addTextChangedListener(textWatcher);
        mOtpFourField.addTextChangedListener(textWatcher);
    }

    public String getOTP() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mOtpOneField.getText().toString());
        stringBuilder.append(mOtpTwoField.getText().toString());
        stringBuilder.append(mOtpThreeField.getText().toString());
        stringBuilder.append(mOtpFourField.getText().toString());
        return stringBuilder.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public SessionManager getSession() {
        return sessionManager;
    }

    @Override
    public void showProgress() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showApiError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_booking_otp_btn_verifyotp: {
                if (getOTP().length()==4) {
                    presenter.verifyOTP(getOTP());
                }else {
                    Toast.makeText(this, getString(R.string.validmobilenumber), Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    public Realm getRealm() {
        return realm;
    }

    @Override
    public void gotoAssingedbooking() {
        startActivity(new Intent(OTPBookingActivity.this, BookingAssingedActivity.class)
        .putExtra("ID",ID));
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
