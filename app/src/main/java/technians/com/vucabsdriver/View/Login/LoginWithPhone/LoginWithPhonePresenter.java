package technians.com.vucabsdriver.View.Login.LoginWithPhone;


import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technians.com.vucabsdriver.Presenter.Presenter;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Model.Login.LoginResponce;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

public class LoginWithPhonePresenter implements Presenter<LoginWithPhoneMVPView> {
    private LoginWithPhoneMVPView loginWithPhoneMVPView;

    @Override
    public void attachView(LoginWithPhoneMVPView view) {
        this.loginWithPhoneMVPView = view;
    }

    @Override
    public void detachView() {
        this.loginWithPhoneMVPView = null;
    }

    public void entermobilenumber(final String mobilenumber) {
        int num_length = mobilenumber.length();
        if (num_length > 0 && num_length < 9) {
            loginWithPhoneMVPView.loaderror(R.string.validmobilenumber);
        } else if (num_length == 0) {
            loginWithPhoneMVPView.loaderror(R.string.madatoryfield);
        } else {
            try {
                loginWithPhoneMVPView.showProgress();
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<LoginResponce> call = apiService.sendOTP(mobilenumber);
                call.enqueue(new Callback<LoginResponce>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponce> call, @NonNull Response<LoginResponce> response) {
                        loginWithPhoneMVPView.hideProgress();
                        if (response.body() != null) {
                            String message = response.body().getMsg();
                            loginWithPhoneMVPView.showMessage(message);
                            if (response.body().getStatus() == 200) {
                                loginWithPhoneMVPView.gotoOTPActivity(mobilenumber);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponce> call, @NonNull Throwable t) {
                        Log.v("Phone123","Error: "+t.getMessage());
                        loginWithPhoneMVPView.hideProgress();
                        String error = new NetworkError(t).getAppErrorMessage();

                        loginWithPhoneMVPView.showApiError(error);
                    }
                });
            }catch (Exception e){
                loginWithPhoneMVPView.showMessage(loginWithPhoneMVPView.getContext().getString(R.string.label_something_went_wrong));
            }
        }
    }
}
