//package technians.com.vucabsdriver.DashboardItems.DriverDuty;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.inputmethod.EditorInfo;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import technians.com.vucabsdriver.AppController;
//import technians.com.vucabsdriver.model.BookingHistory;
//import technians.com.vucabsdriver.R;
//import technians.com.vucabsdriver.Utilities.Constants;
//import technians.com.vucabsdriver.Utilities.DatabaseHandler;
//
//import static technians.com.vucabsdriver.DashboardItems.DriverDuty.DriverDutyFragment.PendingList;
//import static technians.com.vucabsdriver.Utilities.Constants.ShowError;
//import static technians.com.vucabsdriver.Utilities.VolleyErrorHelper.getMessage;
//
//
//public class BookingOtpActivity extends AppCompatActivity implements View.OnKeyListener, TextView.OnEditorActionListener {
//    private String mMobileNumber;
//    private EditText editText1;
//    private EditText editText2;
//    private EditText editText3;
//    private EditText editText4;
//
//    ProgressDialog mProgressDialogObj;
//    DatabaseHandler databaseHandler;
//    BookingHistory bookingHistory;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_booking_otp);
//        bookingHistory = PendingList.get(0);
//        databaseHandler = new DatabaseHandler(BookingOtpActivity.this);
//        mProgressDialogObj = Constants.showProgressDialog(BookingOtpActivity.this);
//        Toolbar toolbar = findViewById(R.id.activity_enterbookingotp_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ImageView mImageViewForward = findViewById(R.id.activity_enterbookingotp_iv_forward);
//        editText1 = findViewById(R.id.activity_bookingotp_et1);
//        editText2 = findViewById(R.id.activity_bookingotp_et2);
//        editText3 = findViewById(R.id.activity_bookingotp_et3);
//        editText4 = findViewById(R.id.activity_bookingotp_et4);
//        editText2.setOnKeyListener(this);
//        editText3.setOnKeyListener(this);
//        editText4.setOnKeyListener(this);
//        editText1.setOnEditorActionListener(this);
//        editText2.setOnEditorActionListener(this);
//        editText3.setOnEditorActionListener(this);
//        editText4.setOnEditorActionListener(this);
//
//        editText1.addTextChangedListener(new TextWatcher() {
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (editText1.getText().toString().length() == 1) {
//                    editText2.requestFocus();
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//
//        });
//
//        editText2.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (editText2.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    editText3.requestFocus();
//                } else if (editText2.getText().toString().length() == 0)     //size as per your requirement
//                {
//                    editText1.requestFocus();
//                    editText1.setSelection(editText1.getText().toString().length());
//                }
//
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//
//        });
//        editText3.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (editText3.getText().toString().length() == 1)     //size as per your requirement
//                {
//                    editText4.requestFocus();
//                } else if (editText3.getText().toString().length() == 0)     //size as per your requirement
//                {
//                    editText2.requestFocus();
//                    editText2.setSelection(editText2.getText().toString().length());
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//
//        });
//
//        editText4.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (editText4.getText().toString().length() == 0)     //size as per your requirement
//                {
//                    editText3.requestFocus();
//                    editText3.setSelection(editText3.getText().toString().length());
//                }
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//
//        });
//
//        mImageViewForward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (editText1.getText().toString().length() != 0 && editText2.getText().toString().length() != 0 &&
//                        editText3.getText().toString().length() != 0 && editText4.getText().toString().length() != 0) {
//                    PerformAction(v);
//                } else {
//                    new Constants().createSnackBar(v, "Please enter valid OTP");
//                }
//            }
//        });
//    }
//
//    private void PerformAction(View v) {
//        String otp = editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString() + editText4.getText().toString();
//        EnterOTP(mMobileNumber, otp, v);
//    }
//
//    private void EnterOTP(final String MobileNumber, final String OTP, final View v) {
//        mProgressDialogObj.show();
//        StringRequest strCommunityRequest = new StringRequest(Request.Method.POST, Constants.URL_ENTERBOOKINGOTP
//                + new Constants().getToken(BookingOtpActivity.this), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    mProgressDialogObj.dismiss();
//                    JSONObject jsonObject = new JSONObject(response);
//                    Boolean success = jsonObject.getBoolean("success");
//                    int status = jsonObject.getInt("status");
//                    // Check for error node in json
//                    if (success && status == 200) {
//                        mProgressDialogObj.dismiss();
//                        startActivity(new Intent(BookingOtpActivity.this, AssingedBooking.class));
//                        finish();
//                    } else if (!success && status == 402) {
//                        mProgressDialogObj.dismiss();
//                        new Constants().createSnackBar(v, "We can't find an otp with this mobile number.");
//                    } else {
//                        mProgressDialogObj.dismiss();
//                        ShowError("Some error occurred", BookingOtpActivity.this);
//                    }
//
//
//                } catch (JSONException e) {
//                    mProgressDialogObj.dismiss();
//                    ShowError("Some error occurred", BookingOtpActivity.this);
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mProgressDialogObj.dismiss();
//                String Error = getMessage(error, BookingOtpActivity.this);
//                ShowError(Error, BookingOtpActivity.this);
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("mobile", bookingHistory.getCustomerMobile());
//                params.put("otp", String.valueOf(OTP));
//                return params;
//            }
//        };
////         Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strCommunityRequest, Constants.TAG_BOOKINGOTP_REQ);
//    }
//
//    @Override
//    public boolean onKey(View view, int i, KeyEvent keyEvent) {
//        if (i == KeyEvent.KEYCODE_DEL) {
//            switch (view.getId()) {
//                case R.id.activity_bookingotp_et2: {
//                    if (editText2.getText().toString().length() == 0)     //size as per your requirement
//                    {
//                        editText1.requestFocus();
//                    }
//                    break;
//                }
//                case R.id.activity_bookingotp_et3: {
//                    if (editText3.getText().toString().length() == 0)     //size as per your requirement
//                    {
//                        editText2.requestFocus();
//                    }
//                    break;
//                }
//                case R.id.activity_bookingotp_et4: {
//                    if (editText4.getText().toString().length() == 0)     //size as per your requirement
//                    {
//                        editText3.requestFocus();
//                    }
//                    break;
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
//        if (editText1.getText().toString().length() != 0 && editText2.getText().toString().length() != 0 &&
//                editText3.getText().toString().length() != 0 && editText4.getText().toString().length() != 0) {
//            if (i == EditorInfo.IME_ACTION_DONE) {
//                switch (textView.getId()) {
//                    case R.id.activity_bookingotp_et1: {
//                        PerformAction(textView);
//                        break;
//                    }
//                    case R.id.activity_bookingotp_et2: {
//                        PerformAction(textView);
//                        break;
//                    }
//                    case R.id.activity_bookingotp_et3: {
//                        PerformAction(textView);
//                        break;
//                    }
//                    case R.id.activity_bookingotp_et4: {
//                        PerformAction(textView);
//                        break;
//                    }
//                }
//            }
//        } else {
//            new Constants().createSnackBar(textView, "Please enter valid OTP");
//        }
//        return false;
//    }
//
//}
//
