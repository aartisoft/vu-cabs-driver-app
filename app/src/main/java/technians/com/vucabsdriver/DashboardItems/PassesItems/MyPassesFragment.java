//package technians.com.vucabsdriver.DashboardItems.PassesItems;
//
//import android.app.ProgressDialog;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.annotation.RequiresApi;
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
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import technians.com.vucabsdriver.AppController;
//import technians.com.vucabsdriver.R;
//import technians.com.vucabsdriver.Utilities.Constants;
//import technians.com.vucabsdriver.Utilities.DatabaseHandler;
//
//import static technians.com.vucabsdriver.Utilities.Constants.ShowError;
//import static technians.com.vucabsdriver.Utilities.Constants.formateDateFromstring;
//import static technians.com.vucabsdriver.Utilities.VolleyErrorHelper.getMessage;
//
//public class MyPassesFragment extends Fragment {
//
//    public MyPassesFragment() {
//        // Required empty public constructor
//    }
//
//    TextView mTextViewTotalRides, mTextViewCreated, mTextViewNoPasses, mTextViewCarType,mTextViewCarNumber,TextViewRecharge;
//    CardView mCardViewMyPasses;
//   ProgressDialog mProgressDialogObj ;
//   DatabaseHandler databaseHandler;
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_my_passes, container, false);
////        mProgressDialogObj = Constants.showProgressDialog(getActivity());
////        mCardViewMyPasses = (CardView) view.findViewById(R.id.layout_mypasses);
////        mTextViewTotalRides = (TextView) view.findViewById(R.id.fragment_mypasses_totalrides);
////        mTextViewCreated = (TextView) view.findViewById(R.id.fragment_mypasses_createddate);
////        mTextViewNoPasses = (TextView) view.findViewById(R.id.fragment_mypasses_tv_nopasses);
////        mTextViewCarType = (TextView) view.findViewById(R.id.fragment_mypasses_cartype);
////        mTextViewCarNumber  = (TextView) view.findViewById(R.id.fragment_mypasses_carnumber);
////        TextViewRecharge = (TextView) view.findViewById(R.id.fragment_mypasses_tv_recharge);
////        databaseHandler = new DatabaseHandler(getActivity());
////        if (databaseHandler.getUserProfileCount()!=0) {
////            mTextViewCarType.setText(FormatCarType(databaseHandler.getcontact().getCar_Type()));
////            mTextViewCarNumber.setText(databaseHandler.getcontact().getCar_Number().toUpperCase());
////        }
////        GetPassesDetails();
//        return view;
//    }
//
//
//    private void GetPassesDetails() {
//        mProgressDialogObj.show();
//
//        StringRequest strCommunityRequest = new StringRequest(Request.Method.POST, Constants.URL_GETPASSES + new Constants().getToken(getActivity()), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                mProgressDialogObj.dismiss();
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Boolean success = jsonObject.getBoolean("success");
//                    int status = jsonObject.getInt("status");
//                    // Check for error node in json
//                    if (success && status == 200) {
//                        mProgressDialogObj.dismiss();
//                        JSONObject data = jsonObject.getJSONObject("data");
//                        int amount = data.getInt("amount");
//                        int ride_qty = data.getInt("ride_qty");
//                        if (ride_qty ==0){
//                            TextViewRecharge.setVisibility(View.VISIBLE);
//                            TextViewRecharge.setText("No rides, please recharge to get bookings.");
//
//                        }else if (ride_qty <2){
//                            TextViewRecharge.setVisibility(View.VISIBLE);
//                            TextViewRecharge.setText("Only few rides left,please recharge.");
//                        }else {
//                            TextViewRecharge.setVisibility(View.GONE);
//                        }
//                        String updated_at = data.getString("updated_at");
//                        String date = formateDateFromstring("yyyy-MM-dd hh:mm:ss", "dd MMMM yyyy", updated_at);
//                        mTextViewTotalRides.setText(String.valueOf(ride_qty));
//                        mTextViewCreated.setText(date);
//                        mTextViewNoPasses.setVisibility(View.GONE);
//                        mCardViewMyPasses.setVisibility(View.VISIBLE);
//                    } else if (!success && status == 402) {
//                        mProgressDialogObj.dismiss();
//                        mTextViewNoPasses.setVisibility(View.VISIBLE);
//                        mCardViewMyPasses.setVisibility(View.GONE);
//                    }else {
//                        mProgressDialogObj.dismiss();
//                        mTextViewNoPasses.setVisibility(View.VISIBLE);
//                        mCardViewMyPasses.setVisibility(View.GONE);
//                    }
//
//                } catch (JSONException e) {
//                    mProgressDialogObj.dismiss();
//                    ShowError("Some error occurred",getActivity());
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mProgressDialogObj.dismiss();
//                String Error = getMessage(error,getActivity());
//                ShowError(Error,getActivity());
//        }
//        });
//
////         Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strCommunityRequest, Constants.TAG_GETPASSES_REQ);
//
//    }
//}
