package technians.com.vucabsdriver.View.Login.LoginWithPhone;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.Login.BaseActivity;
import technians.com.vucabsdriver.View.Login.LoginWithOTP.LoginWithOTPActivity;
import technians.com.vucabsdriver.View.MainView.ContainerActivity.NavigationActivity;

import static technians.com.vucabsdriver.Utilities.Constants.showProgressDialog;


public class LoginWithPhoneActivity extends BaseActivity implements LoginWithPhoneMVPView, View.OnClickListener {

    EditText etphoneno;
    Button btnlogin;
    LoginWithPhonePresenter presenter;
    ProgressDialog progressDialog;
    ImageButton Call, Email;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_with_phone);
        getLayoutInflater().inflate(R.layout.activity_login_with_phone, constraintLayout);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = showProgressDialog(this, getString(R.string.cb_please_wait));
        etphoneno = findViewById(R.id.loginwithphone_et_mobileno);
        btnlogin = findViewById(R.id.loginwithphone_btn_login);
        Call = findViewById(R.id.loginwithphone_ib_call);
        Email = findViewById(R.id.loginwithphone_ib_mail);
        Call.setOnClickListener(this);
        Email.setOnClickListener(this);
        presenter = new LoginWithPhonePresenter();
        presenter.attachView(this);
        if (new SessionManager(this).isLoggedIn()) {
            startActivity(new Intent(LoginWithPhoneActivity.this, NavigationActivity.class));
            finish();
        }
        btnlogin.setOnClickListener(this);
        etphoneno.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    presenter.entermobilenumber(etphoneno.getText().toString());
                    return true;
                }

                return false;
            }
        });
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
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginwithphone_btn_login: {
                presenter.entermobilenumber(etphoneno.getText().toString());
                break;
            }
            case R.id.loginwithphone_ib_call: {
                mDatabase.child("support_number").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String support_number = dataSnapshot.getValue().toString();
                        try {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + support_number));
                            startActivity(callIntent);
                        } catch (ActivityNotFoundException activityException) {
                            Toast.makeText(LoginWithPhoneActivity.this, "Call has failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;
            }
            case R.id.loginwithphone_ib_mail: {
                mDatabase.child("support_email").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String  support_email = dataSnapshot.getValue().toString();
                        try {
                            String[] TO = {support_email};
                            String[] CC = {""};
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);

                            emailIntent.setData(Uri.parse("mailto:"));
                            emailIntent.setType("text/plain");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                            emailIntent.putExtra(Intent.EXTRA_CC, CC);

                            try {
                                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            } catch (android.content.ActivityNotFoundException ex) {
                                Toast.makeText(LoginWithPhoneActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (ActivityNotFoundException activityException) {
                            Toast.makeText(LoginWithPhoneActivity.this, "Email has failed", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                break;
            }
        }
    }

    @Override
    public void loaderror(int stringID) {
        etphoneno.setError(getString(stringID));
    }

    @Override
    public void gotoOTPActivity(String mobilenumber) {
        startActivity(new Intent(LoginWithPhoneActivity.this, LoginWithOTPActivity.class)
                .putExtra("MobileNumber", mobilenumber));
        finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
