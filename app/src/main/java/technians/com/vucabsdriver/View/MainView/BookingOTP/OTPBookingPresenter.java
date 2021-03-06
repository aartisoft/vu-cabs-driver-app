package technians.com.vucabsdriver.View.MainView.BookingOTP;

import android.support.annotation.NonNull;
import android.util.Log;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technians.com.vucabsdriver.Presenter.Presenter;
import technians.com.vucabsdriver.Model.Login.OTPResponce;
import technians.com.vucabsdriver.Model.Profile.Profile;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

public class OTPBookingPresenter implements Presenter<OTPBookingMVPView> {
    private OTPBookingMVPView otpBookingMVPView;

    @Override
    public void attachView(OTPBookingMVPView view) {
        this.otpBookingMVPView = view;
    }

    @Override
    public void detachView() {
        this.otpBookingMVPView = null;
    }

    public void verifyOTP(String otp, String mobileNumber) {
        try {
            otpBookingMVPView.showProgress();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<OTPResponce> call = apiService.bookingverify(otpBookingMVPView.getSession().getToken(), mobileNumber, otp);
            call.enqueue(new Callback<OTPResponce>() {
                @Override
                public void onResponse(@NonNull Call<OTPResponce> call, @NonNull Response<OTPResponce> response) {
                    otpBookingMVPView.hideProgress();
                    Log.v("OTPVerify", "Responce: " + response.body().getMsg());
                    Log.v("OTPVerify", "Responce: " + response.body().toString());
                    Log.v("OTPVerify", "Responce: " + response.body().getSuccess());
                    if (response.body() != null) {

                        //uncomment below code and remove above line

//                        otpBookingMVPView.gotoAssingedbooking();
//                        otpBookingMVPView.getSession().setDrivingActive(true);
//                        otpBookingMVPView.startService();
//                        otpBookingMVPView.changedriverstatus();

                        if (response.body().getStatus() == 200) {
                            otpBookingMVPView.gotoAssingedbooking();
                            otpBookingMVPView.getSession().setDrivingActive(true);
                            otpBookingMVPView.startService();
                            otpBookingMVPView.changedriverstatus();
                        } else {
                            String message = response.body().getMsg();
                            otpBookingMVPView.showMessage(message);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<OTPResponce> call, @NonNull Throwable t) {
                    otpBookingMVPView.hideProgress();
                    String error = new NetworkError(t).getAppErrorMessage();
                    otpBookingMVPView.showApiError(error);
                }
            });
        } catch (Exception e) {
            otpBookingMVPView.showApiError(otpBookingMVPView.getContext().getString(R.string.label_something_went_wrong));
        }
    }
}
