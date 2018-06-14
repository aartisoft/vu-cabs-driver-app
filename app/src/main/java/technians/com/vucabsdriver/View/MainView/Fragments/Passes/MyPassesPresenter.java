package technians.com.vucabsdriver.View.MainView.Fragments.Passes;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technians.com.vucabsdriver.Presenter.Presenter;
import technians.com.vucabsdriver.Model.PassesDetails.Passes;
import technians.com.vucabsdriver.Model.PassesDetails.PassesDetailsResponce;
import technians.com.vucabsdriver.Model.Profile.Profile;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

public class MyPassesPresenter implements Presenter<MyPassesMVPView> {
    private MyPassesMVPView myPassesMVPView;

    @Override
    public void attachView(MyPassesMVPView view) {
        this.myPassesMVPView = view;
    }

    @Override
    public void detachView() {
        this.myPassesMVPView = null;
    }

    public void getPassesDetails() {
        try {
            myPassesMVPView.showProgress();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<PassesDetailsResponce> call = apiService.getPassesDetails(myPassesMVPView.getSession().getToken());
            call.enqueue(new Callback<PassesDetailsResponce>() {
                @Override
                public void onResponse(@NonNull Call<PassesDetailsResponce> call, @NonNull Response<PassesDetailsResponce> response) {

                    myPassesMVPView.hideProgress();

                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            String RidesLeft = response.body().getPassesData().getRecharge_count();
                            String TotalRides = "", RideAmount = "";
                            ArrayList<Passes> arrayList = response.body().getPassesData().getPassesArrayList();
                            Profile profile = myPassesMVPView.getRealm().where(Profile.class).findFirst();
                            for (int i = 0; i < arrayList.size(); i++) {
                                if (arrayList.get(i).getId() == profile.getCar_Type()) {
                                    TotalRides = arrayList.get(i).getRide_qty();
                                    RideAmount = arrayList.get(i).getAmount();
                                }
                            }
                            myPassesMVPView.setdata(RidesLeft, TotalRides, RideAmount);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PassesDetailsResponce> call, @NonNull Throwable t) {
                    myPassesMVPView.hideProgress();
                    String error = new NetworkError(t).getAppErrorMessage();
                    myPassesMVPView.showApiError(error);
                }
            });
        } catch (Exception e) {
            myPassesMVPView.showApiError(myPassesMVPView.getContext().getString(R.string.label_something_went_wrong));
        }
    }
}
