package technians.com.vucabsdriver.View.Login.LoginWithPhone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.Login.LoginWithOTP.LoginWithOTPActivity;
import technians.com.vucabsdriver.View.MainView.AssingedBooking.BookingAssingedActivity;
import technians.com.vucabsdriver.View.MainView.BookingOTP.OTPBookingActivity;
import technians.com.vucabsdriver.View.MainView.ContainerActivity.NavigationActivity;

import static technians.com.vucabsdriver.Utilities.Constants.showProgressDialog;


public class LoginWithPhoneActivity extends AppCompatActivity implements LoginWithPhoneMVPView, View.OnClickListener {
    private static final int MY_PERMISSIONS_REQUEST_CALL = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 2;

    private EditText etphoneno;
    private LoginWithPhonePresenter presenter;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    String support_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = showProgressDialog(this, getString(R.string.cb_please_wait));
        etphoneno = findViewById(R.id.loginwithphone_et_mobileno);
        Button btnlogin = findViewById(R.id.loginwithphone_btn_login);
        ImageButton call = findViewById(R.id.loginwithphone_ib_call);
        ImageButton email = findViewById(R.id.loginwithphone_ib_mail);
        call.setOnClickListener(this);
        email.setOnClickListener(this);
        presenter = new LoginWithPhonePresenter();
        presenter.attachView(this);
        if (new SessionManager(this).isLoggedIn()) {
            startActivity(new Intent(LoginWithPhoneActivity.this, NavigationActivity.class));
            finish();
        }
//        if (new SessionManager(this).isLoggedIn()&& new SessionManager(this).getAssingedStatus()) {
//            startActivity(new Intent(LoginWithPhoneActivity.this, BookingAssingedActivity.class)
//                    .putExtra("ID", new SessionManager(this).getAssingedInt()));
//            finish();
//        }
        btnlogin.setOnClickListener(this);
        etphoneno.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    checkPermissions(MY_PERMISSIONS_REQUEST_READ_SMS);
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
                checkPermissions(MY_PERMISSIONS_REQUEST_READ_SMS);
                break;
            }
            case R.id.loginwithphone_ib_call: {
                try {
                    mDatabase.child("support_number").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            support_number = dataSnapshot.getValue().toString();
                            try {
                                checkPermissions(MY_PERMISSIONS_REQUEST_CALL);
                            } catch (ActivityNotFoundException activityException) {
                                Toast.makeText(LoginWithPhoneActivity.this, "Call has failed", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(this, getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.loginwithphone_ib_mail: {
                mDatabase.child("support_email").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String support_email = dataSnapshot.getValue().toString();
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

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        boolean showRationale = true;
        if (Build.VERSION.SDK_INT >= 23) {
            showRationale = shouldShowRequestPermissionRationale(permissions[0]);
        }
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initializeCall();
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + support_number));
                    startActivity(callIntent);
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
//
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.entermobilenumber(etphoneno.getText().toString());
                } else {
                    if (showRationale) {
                        final MaterialStyledDialog.Builder dialogHeader_1 = new MaterialStyledDialog.Builder(LoginWithPhoneActivity.this)
                                .setIcon(R.drawable.ic_if_setting)
                                .withDialogAnimation(true)
                                .withIconAnimation(true)
                                .setHeaderColorInt(getResources().getColor(R.color.colorAccent))
                                .setDescription(getString(R.string.gotosetting))
                                .setPositiveText(getString(R.string.action_settings))
                                .setNegativeText(getString(R.string.btn_continue))
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        presenter.entermobilenumber(etphoneno.getText().toString());
                                    }
                                })
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                    }
                                });
                        dialogHeader_1.show();
                    } else {
                        presenter.entermobilenumber(etphoneno.getText().toString());
                    }
                }
            }
            break;
        }
    }


    private void checkPermissions(int permissioncode) {
        switch (permissioncode) {
            case MY_PERMISSIONS_REQUEST_CALL: {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(LoginWithPhoneActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(LoginWithPhoneActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL);
                    }else {
                        initializeCall();
                    }
                } else {
                    initializeCall();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (ActivityCompat.checkSelfPermission(LoginWithPhoneActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(LoginWithPhoneActivity.this,
                                new String[]{Manifest.permission.READ_SMS}, MY_PERMISSIONS_REQUEST_READ_SMS);
                    }else{
                        presenter.entermobilenumber(etphoneno.getText().toString());
                    }
                } else {
                    presenter.entermobilenumber(etphoneno.getText().toString());
                }
                break;
            }
        }

    }

    @SuppressLint("MissingPermission")
    private void initializeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + support_number));
        startActivity(callIntent);
    }
}
