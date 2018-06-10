//package technians.com.vucabsdriver.DashboardItems.DriverDuty;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.location.Address;
//import android.location.Geocoder;
//import android.net.Uri;
//import android.os.Build;
//import android.support.annotation.RequiresApi;
//import android.support.design.widget.TextInputLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//
//import technians.com.vucabsdriver.AppController;
//import technians.com.vucabsdriver.BillDetailsActivity;
//import technians.com.vucabsdriver.model.BookingHistory;
//import technians.com.vucabsdriver.R;
//import technians.com.vucabsdriver.Utilities.Constants;
//import technians.com.vucabsdriver.Utilities.DatabaseHandler;
//
//import static technians.com.vucabsdriver.DashboardItems.DriverDuty.DriverDutyFragment.PendingList;
//import static technians.com.vucabsdriver.Utilities.Constants.FormatAmount;
//import static technians.com.vucabsdriver.Utilities.Constants.ShowError;
//import static technians.com.vucabsdriver.Utilities.VolleyErrorHelper.getMessage;
//
//public class AssingedBooking extends AppCompatActivity {
//    TextView mTextViewCustomerName, mTextViewOrderId, mTextViewPickLocation,
//            mTextViewDropLocation, mTextViewDistance, mTextViewTime, mTextViewPaymentType, mTextViewGSTAmount, mTextViewRideAmount, mTextViewTotalAmount;
//    Button mButtonNavigate, mButtonAddToll, mButtonEndTrip;
//    BookingHistory bookingHistory;
//    DatabaseHandler databaseHandler;
//    ProgressDialog mProgressDialogObj;
//    String CurrentAddress = " ";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_assinged_booking);
////        Toolbar toolbar = findViewById(R.id.activity_assingedbooking_toolbar);
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setDisplayShowTitleEnabled(false);
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////
////        mProgressDialogObj = Constants.showProgressDialog(AssingedBooking.this);
////        bookingHistory = PendingList.get(0);
////        databaseHandler = new DatabaseHandler(AssingedBooking.this);
////        mTextViewCustomerName = findViewById(R.id.activity_assingedbooking_custname);
////        mTextViewOrderId = findViewById(R.id.activity_assingedbooking_orderId);
////        mTextViewPickLocation = findViewById(R.id.activity_assingedbooking_pickLocation);
////        mTextViewDropLocation = findViewById(R.id.activity_assingedbooking_dropLocation);
////        mTextViewDistance = findViewById(R.id.activity_assingedbooking_distance);
////        mTextViewTime = findViewById(R.id.activity_assingedbooking_time);
////        mTextViewPaymentType = findViewById(R.id.activity_assingedbooking_paymenttype);
////        mTextViewGSTAmount = findViewById(R.id.activity_assingedbooking_tax);
////        mTextViewRideAmount = findViewById(R.id.activity_assingedbooking_ride_amount);
////        mTextViewTotalAmount = findViewById(R.id.activity_assingedbooking_amount);
////        mButtonNavigate = findViewById(R.id.activity_assingedbooking_btn_startnavigation);
////        mButtonAddToll = findViewById(R.id.activity_assingedbooking_btn_addtoll);
////        mButtonEndTrip = findViewById(R.id.activity_assingedbooking_btn_endtrip);
////        mTextViewCustomerName.setText(bookingHistory.getCustomerName());
////        mTextViewOrderId.setText(bookingHistory.getOrder_ID());
////        mTextViewPickLocation.setText(bookingHistory.getPickLocation());
////        mTextViewDropLocation.setText(bookingHistory.getDropLocation());
////        mTextViewDistance.setText(String.format("%s km", bookingHistory.getTrip_Distance()));
////        mTextViewTime.setText(String.format("%s min", bookingHistory.getTimeDuration()));
////        mTextViewPaymentType.setText(bookingHistory.getPaymentMode());
////        mTextViewGSTAmount.setText(FormatAmount(bookingHistory.getGst_Amount()));
////        mTextViewRideAmount.setText(FormatAmount(bookingHistory.getRide_Amount()));
////        mTextViewTotalAmount.setText(FormatAmount(bookingHistory.getTotal_Amount()));
////        Geocoder geocoder = new Geocoder(AssingedBooking.this, Locale.getDefault());
////        try {
////            List<Address> addresses = geocoder.getFromLocation(databaseHandler.getDriverLocation().getLatitude()
////                    , databaseHandler.getDriverLocation().getLongitude(), 1);
////            if (addresses.size() != 0) {
////                Address obj = addresses.get(0);
////                CurrentAddress = obj.getAddressLine(0);
////            }
////        } catch (IOException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////
////        }
////        mButtonNavigate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Toast.makeText(AssingedBooking.this, "Trip started", Toast.LENGTH_SHORT).show();
////                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
////                        Uri.parse("http://maps.google.com/maps?saddr=" + bookingHistory.getPickLat() + "," + bookingHistory.getPickLng() +
////                                "&daddr=" + bookingHistory.getDropLat() + "," + bookingHistory.getDropLng() + ""));
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                AssingedBooking.this.startActivity(intent);
////            }
////        });
////        mButtonAddToll.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AssingedBooking.this);
////                LayoutInflater inflater = LayoutInflater.from(AssingedBooking.this);
////                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
////                dialogBuilder.setView(dialogView);
////                final EditText TollName = dialogView.findViewById(R.id.et_tollname);
////                final EditText TollPrice = dialogView.findViewById(R.id.et_tollprice);
////                final TextInputLayout TIL_TollName = dialogView.findViewById(R.id.til_tollname);
////                final TextInputLayout TIL_TollPrice = dialogView.findViewById(R.id.til_tollprice);
////                dialogBuilder.setCancelable(false);
////                dialogBuilder.setTitle("Add toll");
////                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
////                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
////                    public void onClick(final DialogInterface dialog, int whichButton) {
////
////                    }
////                });
////                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////                    public void onClick(DialogInterface dialog, int whichButton) {
////                        //pass
////                    }
////                });
////                final AlertDialog b = dialogBuilder.create();
////                b.show();
////                b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        boolean failFlag = false;
////                        CharSequence error = "This can't be empty";
////                        if (TollName.getText().toString().equals("")) {
////                            failFlag = true;
////                            TIL_TollName.setError(error);
////                        } else {
////                            TIL_TollName.setErrorEnabled(false);
////                        }
////                        if (TollPrice.getText().toString().equals("")) {
////                            failFlag = true;
////                            TIL_TollPrice.setError(error);
////                        } else {
////                            TIL_TollPrice.setErrorEnabled(false);
////                        }
////                        if (!failFlag) {
////                            mProgressDialogObj.show();
////                            StringRequest strCommunityRequest = new StringRequest(Request.Method.POST,
////                                    Constants.URL_ADDTOLL + new Constants().getToken(AssingedBooking.this),
////                                    new Response.Listener<String>() {
////                                        @Override
////                                        public void onResponse(String response) {
////                                            mProgressDialogObj.dismiss();
////
////                                            try {
////                                                JSONObject jsonObject = new JSONObject(response);
////                                                Boolean success = jsonObject.getBoolean("success");
////                                                int status = jsonObject.getInt("status");
////                                                // Check for error node in json
////                                                if (success && status == 200) {
////                                                    Toast.makeText(AssingedBooking.this, "Toll added successfully", Toast.LENGTH_SHORT).show();
////                                                    b.dismiss();
////                                                } else if (!success && status == 402) {
////                                                    Toast.makeText(AssingedBooking.this, "Toll data not added", Toast.LENGTH_SHORT).show();
////                                                } else {
////                                                    mProgressDialogObj.dismiss();
////                                                    ShowError("Some error occurred", AssingedBooking.this);
////                                                }
////
////                                            } catch (JSONException e) {
////                                                mProgressDialogObj.dismiss();
////                                                ShowError("Some error occurred", AssingedBooking.this);
////                                                e.printStackTrace();
////                                            }
////
////                                        }
////                                    }, new Response.ErrorListener() {
////                                @Override
////                                public void onErrorResponse(VolleyError error) {
////                                    mProgressDialogObj.dismiss();
////                                    String Error = getMessage(error, AssingedBooking.this);
////                                    ShowError(Error, AssingedBooking.this);
////                                }
////                            }) {
////                                @Override
////                                protected Map<String, String> getParams() {
////                                    Map<String, String> params = new HashMap<String, String>();
////                                    params.put("driver_id", String.valueOf(databaseHandler.getcontact().getDriver_ID()));
////                                    params.put("booking_id", String.valueOf(bookingHistory.getId()));
////                                    params.put("toll_name", TollName.getText().toString());
////                                    params.put("toll_address", CurrentAddress);
////                                    params.put("toll_lat", "" + databaseHandler.getDriverLocation().getLatitude());
////                                    params.put("toll_lng", "" + databaseHandler.getDriverLocation().getLongitude());
////                                    params.put("toll_price", TollPrice.getText().toString());
////                                    return params;
////                                }
////                            };
//////         Adding request to request queue
////                            AppController.getInstance().addToRequestQueue(strCommunityRequest, Constants.TAG_CHANGEDRIVER_REQ);
////                        }
////                    }
////                });
////
////            }
////        });
////
////        mButtonEndTrip.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                mProgressDialogObj.show();
////
////                StringRequest strCommunityRequest = new StringRequest(Request.Method.POST, Constants.URL_TRIPCOMPLETE
////                        + new Constants().getToken(AssingedBooking.this), new Response.Listener<String>() {
////                    @Override
////                    public void onResponse(String response) {
////                        mProgressDialogObj.dismiss();
////                        try {
////                            JSONObject jsonObject = new JSONObject(response);
////                            Boolean success = jsonObject.getBoolean("success");
////                            int status = jsonObject.getInt("status");
////                            // Check for error node in json
////                            if (success && status == 200) {
////                                JSONObject data = jsonObject.getJSONObject("data");
////                                int amount = data.getInt("total_amount");
////                                int toll = data.getInt("toll");
////                                Toast.makeText(AssingedBooking.this, "Trip completed", Toast.LENGTH_SHORT).show();
////                                finish();
////                                Intent intent = new Intent(AssingedBooking.this, BillDetailsActivity.class)
////                                        .putExtra("PaymentMode", bookingHistory.getPaymentMode())
////                                        .putExtra("TotalAmount", amount)
////                                        .putExtra("TollAmount", toll)
////                                        .putExtra("InitialAmount", bookingHistory.getRide_Amount())
////                                        .putExtra("TaxAmount", bookingHistory.getGst_Amount());
////                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                                startActivity(intent);
////                            } else if (!success && status == 402) {
////                                mProgressDialogObj.dismiss();
////                                Toast.makeText(AssingedBooking.this, "Booking status not updated", Toast.LENGTH_SHORT).show();
////                            } else {
////                                mProgressDialogObj.dismiss();
////                                ShowError("Some error occurred", AssingedBooking.this);
////                            }
////
////                        } catch (JSONException e) {
////                            mProgressDialogObj.dismiss();
////                            ShowError("Some error occurred", AssingedBooking.this);
////                            e.printStackTrace();
////                        }
////
////                    }
////                }, new Response.ErrorListener() {
////                    @Override
////                    public void onErrorResponse(VolleyError error) {
////                        mProgressDialogObj.dismiss();
////                        String Error = getMessage(error, AssingedBooking.this);
////                        ShowError(Error, AssingedBooking.this);
////                    }
////                }) {
////                    @Override
////                    protected Map<String, String> getParams() {
////                        Map<String, String> params = new HashMap<String, String>();
////                        params.put("driver_id", String.valueOf(databaseHandler.getcontact().getDriver_ID()));
////                        params.put("order_id", String.valueOf(bookingHistory.getId()));
////                        params.put("address", "xyz");
////                        params.put("lat", "" + bookingHistory.getPickLat());
////                        params.put("lng", "" + bookingHistory.getPickLng());
////                        params.put("status", "1");
////                        return params;
////                    }
////                };
//////         Adding request to request queue
////                strCommunityRequest.setRetryPolicy(new DefaultRetryPolicy(600000, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
////                AppController.getInstance().addToRequestQueue(strCommunityRequest, Constants.TAG_CHANGEDRIVER_REQ);
////
////
////            }
////        });
//    }
//}
//
