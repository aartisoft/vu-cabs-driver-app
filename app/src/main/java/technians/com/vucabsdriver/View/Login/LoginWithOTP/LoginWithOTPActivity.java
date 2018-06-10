package technians.com.vucabsdriver.View.Login.LoginWithOTP;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.TimeUnit;

import technians.com.vucabsdriver.OTPReceivers.OTPListener;
import technians.com.vucabsdriver.OTPReceivers.OtpReader;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.Login.LoginWithPhone.LoginWithPhoneActivity;
import technians.com.vucabsdriver.View.MainView.ContainerActivity.NavigationActivity;

import static technians.com.vucabsdriver.Utilities.Constants.showProgressDialog;

public class LoginWithOTPActivity extends AppCompatActivity implements LoginWithOTPMVPView, View.OnClickListener, OTPListener {
    private static final String TAG = "OTPActivity";
    private EditText mOtpOneField, mOtpTwoField, mOtpThreeField, mOtpFourField, mCurrentlyFocusedEditText;
    private static final String FORMAT = "%02d:%02d";
    TextView mMobileNumber, mTimer;
    Button mBtnResendOTP;

    LoginWithOTPPresenter presenter;
    String MobileNumber;
    ProgressDialog progressDialog;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_otp);
        OtpReader.bind(this, getString(R.string.OTPReader));
        presenter = new LoginWithOTPPresenter();
        presenter.attachView(this);
        progressDialog = showProgressDialog(this, getString(R.string.cb_please_wait));
        MobileNumber = getIntent().getStringExtra("MobileNumber");
        mMobileNumber = findViewById(R.id.activity_enter_otp_mobilenumber);
        mBtnResendOTP = findViewById(R.id.activity_enter_otp_btn_resendOTP);
        mTimer = findViewById(R.id.activity_enter_otp_textView_timer);
        mOtpOneField = findViewById(R.id.otp_one_edit_text);
        mOtpTwoField = findViewById(R.id.otp_two_edit_text);
        mOtpThreeField = findViewById(R.id.otp_three_edit_text);
        mOtpFourField = findViewById(R.id.otp_four_edit_text);
        mMobileNumber.setText(MobileNumber);

        mMobileNumber.setOnClickListener(this);
        mBtnResendOTP.setOnClickListener(this);
        //-----------Start timer-----------
        countDownTimer = new CountDownTimer(120000, 1000) { // adjust the milli seconds here

            @SuppressLint("DefaultLocale")
            public void onTick(long millisUntilFinished) {

                mTimer.setText(String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                presenter.resendOTP(MobileNumber);
            }
        }.start();

        //--------------Set change listners------------

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
                        presenter.checkOTP(MobileNumber, getOTP());
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
    protected void onDestroy() {
        presenter.detachView();
        OtpReader.unbind();
        countDownTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_enter_otp_btn_resendOTP: {
                presenter.resendOTP(MobileNumber);
                break;
            }
            case R.id.activity_enter_otp_mobilenumber: {
                startActivity(new Intent(LoginWithOTPActivity.this, LoginWithPhoneActivity.class));
                finish();
                break;
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public SessionManager getSession() {
        return null;
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
    public void otpReceived(String messageText) {
        String otp = messageText.replaceAll("[^0-9]", "");
        presenter.checkOTP(MobileNumber, otp);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        OtpReader.unbind();
        countDownTimer.cancel();
        super.onPause();
    }

    @Override
    public void gotoNavigationActivity() {
        startActivity(new Intent(LoginWithOTPActivity.this, NavigationActivity.class));
        finish();
    }
}
