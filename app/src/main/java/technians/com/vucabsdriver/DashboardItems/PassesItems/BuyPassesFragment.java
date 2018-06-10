//package technians.com.vucabsdriver.DashboardItems.PassesItems;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.CardView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import technians.com.vucabsdriver.AppController;
//import technians.com.vucabsdriver.PayUMoneyIntegrate.PayUMoneyActivity;
//import technians.com.vucabsdriver.R;
//import technians.com.vucabsdriver.Utilities.Constants;
//import technians.com.vucabsdriver.Utilities.DatabaseHandler;
//import technians.com.vucabsdriver.model.UserProfile;
//
//import static technians.com.vucabsdriver.Utilities.Constants.FormatAmount;
//import static technians.com.vucabsdriver.Utilities.Constants.ShowError;
//import static technians.com.vucabsdriver.Utilities.Constants.showProgressDialog;
//import static technians.com.vucabsdriver.Utilities.VolleyErrorHelper.getMessage;
//
//
//public class BuyPassesFragment extends Fragment {
//
//    CardView mCardViewRecharge;
//    TextView mTextViewCarAmount,mTextViewRides;
//    public String CarType;
//    public TextView mTextViewCarType;
//    int Amount,Rides;
//    DatabaseHandler databaseHandler;
//    UserProfile userProfile;
//    public BuyPassesFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_buy_passes, container, false);
////        databaseHandler = new DatabaseHandler(getActivity());
////        userProfile = databaseHandler.getcontact();
////        mTextViewCarType = (TextView) view.findViewById(R.id.fragment_buy_passes_cartype);
////        mTextViewCarAmount =(TextView) view.findViewById(R.id.fragment_buy_passes_amount);
////        mTextViewRides = (TextView) view.findViewById(R.id.fragment_buy_passes_rides);
////        mCardViewRecharge = (CardView) view.findViewById(R.id.layout_VUMicro);
////        getPriceList();
////        mTextViewCarType.setText(FormatCarType(userProfile.getCar_Type()));
//
//        return view;
//    }
//
//    private void getPriceList() {
//        final ProgressDialog mProgressDialogObj = showProgressDialog(getActivity());
//        mProgressDialogObj.show();
//        StringRequest strCommunityRequest = new StringRequest(Request.Method.POST,
//                Constants.URL_RECHARGE_LIST+new Constants().getToken(getActivity()), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                mProgressDialogObj.dismiss();
//                try {
//                    mProgressDialogObj.dismiss();
//                    JSONObject jsonObject = new JSONObject(response);
//                    Boolean success = jsonObject.getBoolean("success");
//                    int status = jsonObject.getInt("status");
//                    // Check for error node in json
//                    if (success && status == 200) {
//                        JSONArray data = jsonObject.getJSONArray("data");
//                        for (int i=0;i<data.length();i++){
//                            JSONObject value = data.getJSONObject(i);
//                            if (value.getString("car_type").contains(userProfile.getCar_Type())){
//                                Amount = value.getInt("amount");
//                                Rides = value.getInt("ride_qty");
//                                mTextViewCarAmount.setText(FormatAmount(Amount));
//                                mTextViewRides.setText(String.valueOf(Rides));
//                                mCardViewRecharge.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        startActivity(new Intent(getActivity(), PayUMoneyActivity.class)
//                                                .putExtra("Amount", Amount)
//                                                .putExtra("Rides_qty", Rides));
//                                    }
//                                });
//                            }
//                        }
//                    } else {
//                        ShowError("Some error occurred", getActivity());
//                    }
//                } catch (JSONException e) {
//                    mProgressDialogObj.dismiss();
//                    ShowError("Some error occurred", getActivity());
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mProgressDialogObj.dismiss();
//                String Error = getMessage(error, getActivity());
//                ShowError(Error, getActivity());
//            }
//        });
//        AppController.getInstance().addToRequestQueue(strCommunityRequest, Constants.TAG_RECHARGE_PRICELIST_REQ);
//    }
//
//}
