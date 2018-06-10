package technians.com.vucabsdriver.View.MainView.Fragments.RatingFeedback;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technians.com.vucabsdriver.Presenter.Presenter;
import technians.com.vucabsdriver.model.Profile.Profile;
import technians.com.vucabsdriver.model.RatingFeedback.Rating;
import technians.com.vucabsdriver.model.RatingFeedback.RatingResponce;
import technians.com.vucabsdriver.model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

public class RatingFeedbackPresenter implements Presenter<RatingFeedbackMVPView> {
    private RatingFeedbackMVPView ratingFeedbackMVPView;
    private ArrayList<Rating> arrayList = new ArrayList<>();
    @Override
    public void attachView(RatingFeedbackMVPView view) {
        this.ratingFeedbackMVPView = view;
    }

    @Override
    public void detachView() {
        this.ratingFeedbackMVPView = null;
    }

    public void getRatingList() {
        int driverid = ratingFeedbackMVPView.getRealm().where(Profile.class).findFirst().getDriver_ID();
        ratingFeedbackMVPView.showProgress();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<RatingResponce> call = apiService.getRatingfeedback(ratingFeedbackMVPView.getSession().getToken(), driverid);
        call.enqueue(new Callback<RatingResponce>() {
            @Override
            public void onResponse(@NonNull Call<RatingResponce> call, @NonNull Response<RatingResponce> response) {
                ratingFeedbackMVPView.hideProgress();

                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        if (response.body().getRatingArrayList().size()!=0) {
                            arrayList.addAll(response.body().getRatingArrayList());
                            ratingFeedbackMVPView.setAdapter(arrayList);
                        }
                    }else if(response.body().getStatus()==402){
                        ratingFeedbackMVPView.setAdapter(arrayList);
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<RatingResponce> call, @NonNull Throwable t) {
                ratingFeedbackMVPView.hideProgress();
                String error = new NetworkError(t).getAppErrorMessage();
                ratingFeedbackMVPView.showApiError(error);
            }
        });
    }
}
