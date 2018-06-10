package technians.com.vucabsdriver.View.Login.LoginWithOTP;

import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technians.com.vucabsdriver.Presenter.Presenter;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.model.Login.LoginResponce;
import technians.com.vucabsdriver.model.Login.OTPResponce;
import technians.com.vucabsdriver.model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

public class LoginWithOTPPresenter implements Presenter<LoginWithOTPMVPView> {
    private LoginWithOTPMVPView loginWithOTPMVPView;

    @Override
    public void attachView(LoginWithOTPMVPView view) {
        this.loginWithOTPMVPView = view;
    }

    @Override
    public void detachView() {
        this.loginWithOTPMVPView = null;
    }

    public void checkOTP(final String mobileNumber, final String otp) {
        loginWithOTPMVPView.showProgress();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<OTPResponce> call = apiService.checkOTP(mobileNumber,otp);
        call.enqueue(new Callback<OTPResponce>() {
            @Override
            public void onResponse(@NonNull Call<OTPResponce> call, @NonNull Response<OTPResponce> response) {
                loginWithOTPMVPView.hideProgress();
                Log.v("LoginOTP","Responce: "+mobileNumber+ " "+otp);
                Log.v("LoginOTP","Responce: "+response.body());
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        String token = response.body().getToken();
                        new SessionManager(loginWithOTPMVPView.getContext()).setLogin(true,token);
                        loginWithOTPMVPView.gotoNavigationActivity();
                    }else {
                        String message = response.body().getMsg();
                        loginWithOTPMVPView.showMessage(message);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<OTPResponce> call, @NonNull Throwable t) {
                loginWithOTPMVPView.hideProgress();
                String error = new NetworkError(t).getAppErrorMessage();
                loginWithOTPMVPView.showApiError(error);
            }
        });
    }

    public void resendOTP(String mobileNumber) {
        loginWithOTPMVPView.showProgress();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponce> call = apiService.sendOTP(mobileNumber);
        call.enqueue(new Callback<LoginResponce>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponce> call, @NonNull Response<LoginResponce> response) {
                loginWithOTPMVPView.hideProgress();
                if (response.body() != null) {
                    String message = response.body().getMsg();
                    loginWithOTPMVPView.showMessage(message);
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponce> call,@NonNull  Throwable t) {
                loginWithOTPMVPView.hideProgress();
                String error= new NetworkError(t).getAppErrorMessage();

                loginWithOTPMVPView.showApiError(error);
            }
        });
    }

}
