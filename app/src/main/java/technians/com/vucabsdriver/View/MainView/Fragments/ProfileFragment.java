package technians.com.vucabsdriver.View.MainView.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import technians.com.vucabsdriver.AppController;
import technians.com.vucabsdriver.model.UserProfile;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.Constants;

import static technians.com.vucabsdriver.Utilities.Constants.ShowError;
import static technians.com.vucabsdriver.Utilities.VolleyErrorHelper.getMessage;

public class ProfileFragment extends Fragment {
    EditText mEditTextName, mEditTextEmail, mEditTextMobile, mEditTextAddress,mEditTextCarName
            ,mEditTextCarNumber,mEditTextCarType,mEditTextCarSeat;
    CircleImageView mCircleProfile;
    ProgressDialog mProgressDialogObj;
    TextView mTextViewStatus,mTextViewMessage;
    ImageView mImageViewCarPic;
    int CarDrawable ;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//        databaseHandler = new DatabaseHandler(getActivity());
//        mProgressDialogObj = Constants.showProgressDialog(getActivity());
//        mEditTextName = (EditText) view.findViewById(R.id.fragment_profile_et_name);
//        mEditTextMobile = (EditText) view.findViewById(R.id.fragment_profile_et_mobilenum);
//        mEditTextEmail = (EditText) view.findViewById(R.id.fragment_profile_et_email);
//        mEditTextAddress = (EditText) view.findViewById(R.id.fragment_profile_et_Address);
//        mEditTextCarName = (EditText) view.findViewById(R.id.fragment_profile_et_CarName);
//        mEditTextCarNumber = (EditText) view.findViewById(R.id.fragment_profile_et_CarNumber);
//        mEditTextCarType = (EditText) view.findViewById(R.id.fragment_profile_et_CarType);
//        mEditTextCarSeat = (EditText) view.findViewById(R.id.fragment_profile_et_CarSeat);
//        mCircleProfile = view.findViewById(R.id.user_profile_photo);
//        mTextViewStatus = view.findViewById(R.id.fragment_profile_tv_status);
//        mTextViewMessage = view.findViewById(R.id.fragment_profile_tv_message);
//        mImageViewCarPic = view.findViewById(R.id.user_car_photo);
//        GetUserProfile();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.profile));
    }
    private void GetUserProfile() {
        mProgressDialogObj.show();
        StringRequest strCommunityRequest = new StringRequest(Request.Method.POST, Constants.URL_DRIVERPROFILE +
                new Constants().getToken(getActivity()), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialogObj.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Boolean success = jsonObject.getBoolean("success");
                    int status = jsonObject.getInt("status");
                    // Check for error node in json
                    if (success && status == 200) {
                        mProgressDialogObj.dismiss();
                        JSONObject data = jsonObject.getJSONObject("data");
                        int driverid = data.getInt("id");
                        int driver_status = data.getInt("status");
                        String name = data.getString("name");
                        String email = data.getString("email");
                        String mobile = data.getString("mobile");
                        String address = data.getString("address");
                        String car_name = data.getString("car_name");
                        String car_number = data.getString("car_no");
                        String car_seat = data.getString("car_seat");
                        String car_type = data.getString("car_type");
                        String car_URL = data.getString("car_img");
                        String profileURL = data.getString("profile_img");


                        mEditTextName.setText(name);
                        mEditTextMobile.setText(mobile);
                        mEditTextEmail.setText(email);
                        mEditTextAddress.setText(address);
                        mEditTextCarName.setText(car_name);
                        mEditTextCarNumber.setText(car_number);
                        mEditTextCarType.setText(String.format("%s %s", car_type.substring(0, 2).toUpperCase(Locale.getDefault()), car_type.substring(2)));
                        mEditTextCarSeat.setText(String.valueOf(car_seat));
                        Glide.with(getActivity()).load(profileURL).placeholder(R.drawable.ic_account_circle_blue_grey_300_48dp).dontAnimate()
                                .override(400, 400).diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(mCircleProfile);
                        switch (car_type.toUpperCase()) {
                            case "VUMICRO": {
                                CarDrawable = R.drawable.micro_car;
                                break;
                            }
                            case "VUMINI": {
                                CarDrawable = R.drawable.mini_car;
                                break;
                            }
                            case "VUSEDAN": {
                                CarDrawable = R.drawable.sedan_car;
                                break;
                            }
                            case "VUSUV": {
                                CarDrawable = R.drawable.suv_car;
                                break;
                            }
                        }
                        Glide.with(getActivity()).load(car_URL).placeholder(CarDrawable).dontAnimate()
                                .override(400, 400).diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(mImageViewCarPic);
                        if (driver_status == 0) {
                            mTextViewStatus.setText("Deactive");
                            mTextViewStatus.setTextColor(Color.RED);
                            mTextViewMessage.setTextColor(Color.RED);
                        }else {
                            mTextViewStatus.setText("Active");
                            mTextViewStatus.setTextColor(Color.BLUE);
                            mTextViewMessage.setVisibility(View.GONE);
                        }
                    }else if(!success&&status==401){
                        mProgressDialogObj.dismiss();
                        Toast.makeText(getActivity(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mProgressDialogObj.dismiss();
                        ShowError("Some error occurred",getActivity());
                    }

                } catch (JSONException e) {
                    mProgressDialogObj.dismiss();
                    ShowError("Some error occurred", getActivity());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialogObj.dismiss();
                String Error = getMessage(error, getActivity());
                ShowError(Error, getActivity());
            }
        });
//         Adding request to request queue
        AppController.getInstance().addToRequestQueue(strCommunityRequest, Constants.TAG_DRIVERPROFILE);
    }
}
