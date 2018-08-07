package technians.com.vucabsdriver.View.MainView.Fragments.MyProfile;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import technians.com.vucabsdriver.RealmController1;
import technians.com.vucabsdriver.RealmHelper;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.Model.Profile.Profile;
import technians.com.vucabsdriver.Model.Profile.ProfileResponce;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;


public class ProfileFragment extends Fragment {
    private TextView mTextViewName, mTextViewStatus, mTextViewMobile, mTextViewEmail, mTextViewAddress,mTextViewAadhar, mTextViewCarName, mTextViewCarNumber, mTextViewCarType, mTextViewCarSeat;

    private CircleImageView circleImageView;

    private ProgressDialog progressDialog;
    private Realm realm;
    private RealmHelper helper;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        progressDialog = Constants.showProgressDialog(getActivity());
        mTextViewName = view.findViewById(R.id.fragment_profile_name);
        mTextViewStatus = view.findViewById(R.id.fragment_profile_driverstatus);
        mTextViewMobile = view.findViewById(R.id.fragment_profile_mobile);
        mTextViewEmail = view.findViewById(R.id.fragment_profile_email);
        mTextViewAddress = view.findViewById(R.id.fragment_profile_address);
        mTextViewAadhar = view.findViewById(R.id.fragment_profile_aadharno);
        mTextViewCarName = view.findViewById(R.id.fragment_profile_carname);
        mTextViewCarNumber = view.findViewById(R.id.fragment_profile_carnumber);
        mTextViewCarType = view.findViewById(R.id.fragment_profile_cartype);
        mTextViewCarSeat = view.findViewById(R.id.fragment_profile_carseat);
        circleImageView = view.findViewById(R.id.fragment_profile_userimage);
        RealmController1 realmController1 = new RealmController1(getContext());
        realm= Realm.getInstance(realmController1.initializeDB());
        helper=new RealmHelper(realm);

        getUserProfile();

        return view;
    }

    @Override
    public void onDestroyView() {
        realm.close();
        super.onDestroyView();
    }

    private void getUserProfile() {
        try {
            progressDialog.show();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ProfileResponce> call = apiService.getProfile(new SessionManager(getActivity()).getToken());
            call.enqueue(new Callback<ProfileResponce>() {
                @Override
                public void onResponse(@NonNull Call<ProfileResponce> call, @NonNull retrofit2.Response<ProfileResponce> response) {
                    progressDialog.dismiss();
                    Log.v("ProfileFragment","Responce: "+new SessionManager(getActivity()).getToken());
                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            Profile profile = response.body().getProfile();
                            helper.saveProfile(profile);
//                            realm.beginTransaction();
//                            realm.copyToRealmOrUpdate(profile);
//                            realm.commitTransaction();
                            mTextViewName.setText(profile.getName());
                            if (profile.getDriver_Status() == 0) {
                                mTextViewStatus.setText("Deactive");
                            } else if (profile.getDriver_Status() == 1) {
                                mTextViewStatus.setText("Active");
                            }
                            mTextViewMobile.setText(profile.getMobileNumber());
                            mTextViewEmail.setText(profile.getEmail());
                            mTextViewAddress.setText(profile.getAddress());
                            mTextViewCarName.setText(profile.getCar_Name());
                            mTextViewCarNumber.setText(profile.getCar_Number());
                            mTextViewCarType.setText(profile.getCar_Type_Name());
                            mTextViewCarSeat.setText(profile.getCar_Seat());
                            mTextViewAadhar.setText(profile.getAadhar_no());
                            Glide.with(getActivity()).load(profile.getProfileURL()).placeholder(R.drawable.ic_user).dontAnimate().into(circleImageView);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProfileResponce> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    String error = new NetworkError(t).getAppErrorMessage();
                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getString(R.string.profile));
    }

}
