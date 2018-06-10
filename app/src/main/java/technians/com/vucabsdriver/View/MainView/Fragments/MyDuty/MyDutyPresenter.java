package technians.com.vucabsdriver.View.MainView.Fragments.MyDuty;

import android.support.annotation.NonNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technians.com.vucabsdriver.Presenter.Presenter;
import technians.com.vucabsdriver.model.PendingRequest.BookingData;
import technians.com.vucabsdriver.model.PendingRequest.PendingRequestResponce;
import technians.com.vucabsdriver.model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

public class MyDutyPresenter implements Presenter<MyDutyMVPView> {
    private MyDutyMVPView myDutyMVPView;

    @Override
    public void attachView(MyDutyMVPView view) {
        this.myDutyMVPView = view;
    }

    @Override
    public void detachView() {
        this.myDutyMVPView = null;
    }

    public void loadpendingrequest() {
        myDutyMVPView.showProgress();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<PendingRequestResponce> call = apiService.getPendingRequest(myDutyMVPView.getSession().getToken());
        call.enqueue(new Callback<PendingRequestResponce>() {
            @Override
            public void onResponse(@NonNull Call<PendingRequestResponce> call, @NonNull Response<PendingRequestResponce> response) {
                myDutyMVPView.hideProgress();
                if (response.body() != null) {
                    if (response.body().getStatus() == 200) {
                        ArrayList<BookingData> arrayList = response.body().getPendingRequestData().getBookingData();
                        myDutyMVPView.getRealm().beginTransaction();
                        myDutyMVPView.getRealm().where(BookingData.class).findAll().deleteAllFromRealm();
                        myDutyMVPView.getRealm().commitTransaction();

                        if (arrayList.size()==0){
                            myDutyMVPView.nobooking();
                        }else {
                            for (int i = 0; i < arrayList.size(); i++) {
                                BookingData bookingData = arrayList.get(i);
                                myDutyMVPView.getRealm().beginTransaction();
                                myDutyMVPView.getRealm().copyToRealmOrUpdate(bookingData);
                                myDutyMVPView.getRealm().commitTransaction();
                                if (arrayList.get(i).getStatus() == 0) {
                                    BookingData pendingdata = arrayList.get(i);
                                    myDutyMVPView.setData(pendingdata);
                                    break;
                                } else {
                                    myDutyMVPView.nobooking();
                                }
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PendingRequestResponce> call, @NonNull Throwable t) {
                myDutyMVPView.hideProgress();
                String error = new NetworkError(t).getAppErrorMessage();
                myDutyMVPView.showApiError(error);
            }
        });
    }
}
