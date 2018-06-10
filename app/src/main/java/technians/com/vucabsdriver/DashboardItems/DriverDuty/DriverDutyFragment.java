//package technians.com.vucabsdriver.DashboardItems.DriverDuty;
//
//import android.app.ActivityManager;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.location.Address;
//import android.location.Geocoder;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.Objects;
//
//import technians.com.vucabsdriver.AppController;
//import technians.com.vucabsdriver.BackgroundFusedLocation;
//import technians.com.vucabsdriver.model.BookingHistory;
//import technians.com.vucabsdriver.model.DriverLocation;
//import technians.com.vucabsdriver.R;
//import technians.com.vucabsdriver.Utilities.Constants;
//import technians.com.vucabsdriver.Utilities.DatabaseHandler;
//
//import static android.content.Context.MODE_PRIVATE;
//import static technians.com.vucabsdriver.Utilities.Constants.FormatAmount;
//import static technians.com.vucabsdriver.Utilities.Constants.ShowError;
//import static technians.com.vucabsdriver.Utilities.VolleyErrorHelper.getMessage;
//
//public class DriverDutyFragment extends Fragment {
//
//
//    public DriverDutyFragment() {
//        // Required empty public constructor
//    }
//
//
//    ToggleButton mToggleButton_DriverDuty;
//    int DriverDutyStatus;
//    public static ArrayList<BookingHistory> BookingHistory_List = new ArrayList<>();
//    public static List<BookingHistory> PendingList = new ArrayList<>();
//    private RecyclerView recyclerView;
//    LinearLayout mLinearLayoutOnline, mLinearLayoutOffline;
//    CardView layout_myLocation;
//    Button mButtonRefresh;
//    TextView mTextViewNoBookings, mTextViewBookingCount, mTextViewTotalEarning, mTextViewDriverLocation, mTextViewRecharge;
//    ProgressDialog mProgressDialogObj, mProgressDialogLocation;
//    DatabaseHandler databaseHandler;
//    DatabaseReference mDatabase;
//    int DriverId;
//    private Intent intent;
//    String CurrentDriverAddress;
//    Double CurrentLatitude, CurrentLongitude;
//    CardView cardView_BookingList;
//    boolean shouldExecuteOnResume;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Log.v("DriverDuty123","onAttach");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        // Inflate the layout for this fragment
//        final View view = inflater.inflate(R.layout.fragment_driver_duty, container, false);
////        shouldExecuteOnResume = false;
////        if (!isMyServiceRunning(BackgroundFusedLocation.class)) {
////            getActivity().startService(new Intent(getActivity(), BackgroundFusedLocation.class));
////        }
////        intent = new Intent(getActivity(), BackgroundFusedLocation.class);
////        databaseHandler = new DatabaseHandler(getActivity());
////        DriverId = databaseHandler.getcontact().getDriver_ID();
////        mDatabase = FirebaseDatabase.getInstance().getReference().child("driver_location").child(String.valueOf(DriverId));
////        mProgressDialogObj = Constants.showProgressDialog(getActivity());
////        mProgressDialogLocation = new ProgressDialog(getActivity());
////        mProgressDialogLocation.setMessage("Fetching Location");
////        mProgressDialogLocation.setProgressStyle(ProgressDialog.STYLE_SPINNER);
////        mProgressDialogLocation.setCancelable(false);
////        mProgressDialogLocation.show();
////        mButtonRefresh = view.findViewById(R.id.refresh);
////        mToggleButton_DriverDuty = view.findViewById(R.id.fragment_duty_switch);
////        recyclerView = view.findViewById(R.id.fragment_driverduty_recycler_view);
////        mLinearLayoutOnline = view.findViewById(R.id.fragment_duty_layout_onduty);
////        mLinearLayoutOffline = view.findViewById(R.id.fragment_duty_layout_offduty);
////        layout_myLocation = view.findViewById(R.id.fragment_duty_layout_myLocation);
////        mTextViewNoBookings = view.findViewById(R.id.textview_emptylist);
////        mTextViewBookingCount = view.findViewById(R.id.textview_bookingcount);
////        mTextViewTotalEarning = view.findViewById(R.id.textview_earning);
////        mTextViewDriverLocation = view.findViewById(R.id.fragment_duty_tv_pickup);
////        mTextViewRecharge = view.findViewById(R.id.fragment_driverduty_tv_recharge);
////        cardView_BookingList = view.findViewById(R.id.card_view_bookinglist);
////        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
////        recyclerView.setLayoutManager(mLayoutManager);
////        recyclerView.setItemAnimator(new DefaultItemAnimator());
////
////        SharedPreferences driver_duty_status = getActivity().getSharedPreferences("DRIVER_DUTY_STATUS", MODE_PRIVATE);
////        DriverDutyStatus = driver_duty_status.getInt("DriverDutyStatus", 3);
////
////
////        if (DriverId != 0) {
////            ValueEventListener postListener = new ValueEventListener() {
////                @Override
////                public void onDataChange(DataSnapshot dataSnapshot) {
////                    // Get Post object and use the values to update the UI
////                    if (dataSnapshot.getValue(DriverLocation.class) != null) {
////                        DriverLocation driverLocation = dataSnapshot.getValue(DriverLocation.class);
////                        if (driverLocation != null) {
////                            mProgressDialogLocation.dismiss();
////                            mTextViewDriverLocation.setText(driverLocation.getLocationAddress());
////                            if (databaseHandler.getDriverLocationCount() == 0) {
////                                databaseHandler.addlocation(driverLocation);
//////                                GetDriverState();
////                            }
////
////                        }
////
////                    } else {
////                        mProgressDialogLocation.dismiss();
////                        mTextViewDriverLocation.setText("Could not get current location");
////                    }
////                }
////
////                @Override
////                public void onCancelled(DatabaseError databaseError) {
////                    // Getting Post failed, log a message
////                    mProgressDialogLocation.dismiss();
////                }
////            };
////            mDatabase.addValueEventListener(postListener);
////        } else {
////            mProgressDialogLocation.dismiss();
////            mTextViewDriverLocation.setText("Could not get current location");
////        }
////
////        if (databaseHandler.getDriverLocationCount() != 0) {
////            changeDriverStatus(1);
////        }
////
////        mToggleButton_DriverDuty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
////            @Override
////            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
////                int status;
////                if (b) {
////                    status = 1;
////                } else {
////                    status = 0;
////                }
////                changeDriverStatus(status);
////            }
////        });
////
////        mButtonRefresh.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                getBookingRequest();
////            }
////        });
//        return view;
//
//    }
//
//    private boolean isMyServiceRunning(Class<?> serviceClass) {
//        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
//        assert manager != null;
//        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceClass.getName().equals(service.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }
//
////    public void changeDriverStatus(final int driver_status) {
////        mProgressDialogObj.show();
////        final int driver_id = databaseHandler.getcontact().getDriver_ID();
////
////        if (databaseHandler.getDriverLocationCount()==0){
////            CurrentLatitude = 0d;
////            CurrentLongitude= 0d;
////            CurrentDriverAddress = "Unknown Location";
////
////        }else {
////            CurrentLatitude = databaseHandler.getDriverLocation().getLatitude();
////            CurrentLongitude= databaseHandler.getDriverLocation().getLongitude();
////            CurrentDriverAddress = databaseHandler.getDriverLocation().getLocationAddress();
////        }
////        StringRequest strCommunityRequest = new StringRequest(Request.Method.POST, Constants.URL_ACTIVEDRIVERS +
////                new Constants().getToken(getActivity()), new Response.Listener<String>() {
////            @Override
////            public void onResponse(String response) {
////                mProgressDialogObj.dismiss();
////                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    Boolean success = jsonObject.getBoolean("success");
////                    int status = jsonObject.getInt("status");
////                    // Check for error node in json
////                    if (success && status == 200) {
////                        SharedPreferences.Editor editor = getActivity().
////                                getSharedPreferences("DRIVER_DUTY_STATUS", MODE_PRIVATE).edit();
////                        editor.putInt("DriverDutyStatus", driver_status);
////                        editor.apply();
////                        if (driver_status == 0) {
////                            mToggleButton_DriverDuty.setChecked(false);
////                            getActivity().stopService(new Intent(getActivity(), BackgroundFusedLocation.class));
////                            cardView_BookingList.setVisibility(View.GONE);
////                        } else if (driver_status == 1) {
////                            mToggleButton_DriverDuty.setChecked(true);
////                            getActivity().startService(new Intent(getActivity(), BackgroundFusedLocation.class));
////                            cardView_BookingList.setVisibility(View.VISIBLE);
////                        }
////                        getBookingRequest();
////                    } else if (!success && status == 401) {
////                        ShowError("Some error occurred", getActivity());
////                    } else {
////                        ShowError("Some error occurred", getActivity());
////                    }
////
////                } catch (JSONException e) {
////                    ShowError("Some error occurred", getActivity());
////                    e.printStackTrace();
////                }
////
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                mProgressDialogObj.dismiss();
////                String Error = getMessage(error, getActivity());
////                ShowError(Error, getActivity());
////            }
////        }) {
////            @Override
////            protected Map<String, String> getParams() {
////                Map<String, String> params = new HashMap<String, String>();
////                params.put("driver_id", String.valueOf(driver_id));
////                params.put("address", CurrentDriverAddress);
////                params.put("lat", "" + CurrentLatitude);
////                params.put("lng", "" + CurrentLongitude);
////                params.put("status", String.valueOf(driver_status));
////                return params;
////            }
////        };
//////         Adding request to request queue
////        AppController.getInstance().addToRequestQueue(strCommunityRequest, Constants.TAG_CHANGEDRIVER_REQ);
////
////    }
////
////    public void getBookingRequest() {
////        mProgressDialogObj.show();
////        final StringRequest strCommunityRequest = new StringRequest(Request.Method.POST,
////                Constants.URL_BOOKINGHISTORY + new Constants().getToken(getActivity()), new Response.Listener<String>() {
////            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
////            @Override
////            public void onResponse(String response) {
////                mProgressDialogObj.dismiss();
////                try {
////                    JSONObject jsonObject = new JSONObject(response);
////                    Boolean success = jsonObject.getBoolean("success");
////                    int Status = jsonObject.getInt("status");
////                    // Check for error node in json
////                    if (success && Status == 200) {
////                        PendingList.clear();
////                        BookingHistory_List.clear();
////                        JSONObject data = jsonObject.getJSONObject("data");
////                        JSONArray booking_data = data.getJSONArray("booking_data");
////                        int total_earn = Integer.parseInt(data.getString("total_earn"));
////                        int total_booking = Integer.parseInt(data.getString("total_booking"));
////                        int recharge_count = Integer.parseInt(data.getString("recharge_count"));
////                        if (recharge_count == 0) {
////                            mTextViewRecharge.setVisibility(View.VISIBLE);
////                            mTextViewRecharge.setText("No rides, please recharge to get bookings.");
////
////                        } else if (recharge_count < 2) {
////                            mTextViewRecharge.setVisibility(View.VISIBLE);
////                            mTextViewRecharge.setText("Only few rides left,please recharge.");
////                        }
////                        mTextViewTotalEarning.setText(FormatAmount(total_earn));
////                        mTextViewBookingCount.setText(String.valueOf(total_booking));
////                        for (int i = booking_data.length() - 1; i >= 0; i--) {
////                            mProgressDialogObj.dismiss();
////                            JSONObject json_data = booking_data.getJSONObject(i);
////                            String customer_name = json_data.getString("customer_name");
////                            String customer_mobile = json_data.getString("customer_mobile");
////                            String customer_email = json_data.getString("customer_email");
////                            int id = json_data.getInt("id");
////                            String order_id = json_data.getString("order_id");
////                            String pick_loc = json_data.getString("pick_loc");
////                            Double pick_lat = json_data.getDouble("pick_lat");
////                            Double pick_lng = json_data.getDouble("pick_lng");
////                            String drop_loc = json_data.getString("drop_loc");
////                            Double drop_lat = json_data.getDouble("drop_lat");
////                            Double drop_lng = json_data.getDouble("drop_lng");
////                            String payment_type = json_data.getString("payment_type");
////                            String distance = json_data.getString("distance");
////                            String time_duration = json_data.getString("time_duration");
////                            int toll = json_data.getInt("toll");
////                            int gst_amount = json_data.getInt("gst_amount");
////                            int ride_amount = json_data.getInt("ride_amount");
////                            int total_amount = json_data.getInt("total_amount");
////                            int status = json_data.getInt("status");
////                            String date = json_data.getString("date");
////                            if (status == 0) {
////                                PendingList.add(new BookingHistory(customer_name, customer_mobile, customer_email, pick_loc, order_id,
////                                        drop_loc, payment_type, distance, date, time_duration, "Pending", toll, gst_amount, ride_amount, total_amount,
////                                        id, status, total_earn, total_booking, pick_lat, pick_lng, drop_lat, drop_lng));
////
////                            }
////                            if (status == 0 || status == 1 || status == 2) {
////                                String BookingStatus = null;
////                                String Payment_Type = null;
////                                switch (status) {
////                                    case 1: {
////                                        BookingStatus = "Completed";
////                                        break;
////                                    }
////                                    case 2: {
////                                        BookingStatus = "Cancelled";
////                                        break;
////                                    }
////                                    case 0: {
////                                        BookingStatus = "Pending";
////                                        break;
////                                    }
////                                    default: {
////                                        Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
////                                    }
////                                }
////                                if (Objects.equals(payment_type.toUpperCase(), "CASH") || Objects.equals(payment_type.toUpperCase(), "COD") || Objects.equals(payment_type.toUpperCase(), "PAYU")) {
////
////                                    switch (payment_type.toUpperCase()) {
////                                        case "COD": {
////                                            Payment_Type = "Cash";
////                                            break;
////                                        }
////                                        case "CASH": {
////                                            Payment_Type = "Cash";
////                                            break;
////                                        }
////                                        case "PAYU": {
////                                            Payment_Type = "PayU";
////                                            break;
////                                        }
////                                        default: {
////                                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////                                }
////                                BookingHistory_List.add(new BookingHistory(customer_name, customer_mobile, customer_email, pick_loc, order_id,
////                                        drop_loc, Payment_Type, distance, date, time_duration, BookingStatus, toll, gst_amount, ride_amount, total_amount,
////                                        id, status, total_earn, total_booking, pick_lat, pick_lng, drop_lat, drop_lng));
////                            }
////                            preparePendingList(PendingList);
////                        }
////                    } else if (!success && Status == 401) {
////                        mProgressDialogObj.dismiss();
////                        Toast.makeText(getActivity(), "Could not load booking", Toast.LENGTH_SHORT).show();
////                    } else {
////                        mProgressDialogObj.dismiss();
////                        ShowError("Some error occurred", getActivity());
////                    }
////
////                } catch (JSONException e) {
////                    mProgressDialogObj.dismiss();
////                    ShowError("Some error occurred", getActivity());
////                    e.printStackTrace();
////                }
////
////            }
////        }, new Response.ErrorListener() {
////            @Override
////            public void onErrorResponse(VolleyError error) {
////                mProgressDialogObj.dismiss();
////                String Error = getMessage(error, getActivity());
////                ShowError(Error, getActivity());
////            }
////        });
//////         Adding request to request queue
////        AppController.getInstance().addToRequestQueue(strCommunityRequest, Constants.TAG_DRIVERDOC_REQ);
////
////    }
////
////    private void preparePendingList(List<BookingHistory> pendingList) {
////        if (pendingList.size() != 0) {
////            mTextViewNoBookings.setVisibility(View.GONE);
////            recyclerView.setVisibility(View.VISIBLE);
////            NewBoookingAdapter mAdapter = new NewBoookingAdapter(pendingList, getActivity());
////            recyclerView.setAdapter(mAdapter);
////            mAdapter.notifyDataSetChanged();
////        } else {
//            mTextViewNoBookings.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }
//    }
//
//
//
//
//}
