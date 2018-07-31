package technians.com.vucabsdriver.View.MainView.AssingedBooking;

import android.support.annotation.NonNull;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technians.com.vucabsdriver.Model.TripEnd.DataResponce;
import technians.com.vucabsdriver.Presenter.Presenter;
import technians.com.vucabsdriver.Model.DriverLocationPackage.DriverLocation;
import technians.com.vucabsdriver.Model.Login.LoginResponce;
import technians.com.vucabsdriver.Model.PendingRequest.BookingData;
import technians.com.vucabsdriver.Model.Profile.Profile;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.Model.TripEnd.TripEndData;
import technians.com.vucabsdriver.Model.TripEnd.TripEndResponce;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

import static technians.com.vucabsdriver.Utilities.Constants.getLocationAddress;

public class BookingAssingedPresenter implements Presenter<BookingAssingedMVPView> {
    private BookingAssingedMVPView bookingAssingedMVPView;

    @Override
    public void attachView(BookingAssingedMVPView view) {
        this.bookingAssingedMVPView = view;
    }

    @Override
    public void detachView() {
        this.bookingAssingedMVPView = null;
    }

    public void Addtoll(Profile profile, DriverLocation driverLocation, BookingData bookingData, String tollAddress, String tollPrice) {

        try {
            bookingAssingedMVPView.showProgress();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<LoginResponce> call = apiService.addToll(bookingAssingedMVPView.getSession().getToken(), String.valueOf(profile.getDriver_ID()),
                    String.valueOf(bookingData.getId()), tollAddress, getLocationAddress(driverLocation.getLatitude(), driverLocation.getLongitude(),
                            bookingAssingedMVPView.getContext()), String.valueOf(driverLocation.getLatitude()), String.valueOf(driverLocation.getLongitude())
                    , tollPrice);
            call.enqueue(new Callback<LoginResponce>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponce> call, @NonNull Response<LoginResponce> response) {
                    bookingAssingedMVPView.hideProgress();
                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            bookingAssingedMVPView.dialogdismiss();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponce> call, @NonNull Throwable t) {
                    bookingAssingedMVPView.hideProgress();
                    String error = new NetworkError(t).getAppErrorMessage();
                    bookingAssingedMVPView.showApiError(error);
                }
            });
        } catch (Exception e) {
            bookingAssingedMVPView.showApiError(bookingAssingedMVPView.getContext().getString(R.string.label_something_went_wrong));
        }
    }

    public void endTrip(Profile profile, DriverLocation driverLocation, final BookingData bookingData) {
        try {

            double Distance = Float.valueOf(bookingAssingedMVPView.getSession().getRideDistance()) * 0.001;
            int carid = bookingAssingedMVPView.getRealm().where(Profile.class).findFirst().getCar_Type();
            bookingAssingedMVPView.showProgress();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<TripEndResponce> call = apiService.tripEnd(bookingAssingedMVPView.getSession().getToken(), String.valueOf(profile.getDriver_ID())
                    , String.valueOf(bookingData.getId()), getLocationAddress(driverLocation.getLatitude(), driverLocation.getLongitude(),
                            bookingAssingedMVPView.getContext()), driverLocation.getLatitude(), driverLocation.getLongitude(), 1,
                    Distance,bookingData.getTime_duration(),carid);

            call.enqueue(new Callback<TripEndResponce>() {
                @Override
                public void onResponse(@NonNull Call<TripEndResponce> call, @NonNull Response<TripEndResponce> response) {
                    bookingAssingedMVPView.hideProgress();
                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            if (response.body() != null) {

                                TripEndData data = response.body().getResponce().getInvoice();
                                bookingAssingedMVPView.getRealm().beginTransaction();
                                BookingData booking = bookingAssingedMVPView.getRealm().where(BookingData.class).equalTo("id", bookingData.getId()).findFirst();
                                booking.setGst_amount(Math.round(Float.valueOf(data.getGst_amount())));
                                booking.setTotal_amount(Math.round(Float.valueOf(data.getTotal_amount())));
                                booking.setRide_amount(Math.round(Float.valueOf(data.getRide_amount())));
                                booking.setToll(data.getToll());
                                bookingAssingedMVPView.getRealm().commitTransaction();
                            }
                            bookingAssingedMVPView.gotoTripCompleteActivity();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<TripEndResponce> call, @NonNull Throwable t) {
                    bookingAssingedMVPView.hideProgress();
                    String error = new NetworkError(t).getAppErrorMessage();
                    bookingAssingedMVPView.showApiError(error);
                }
            });
        }catch (Exception e) {
            bookingAssingedMVPView.showApiError(bookingAssingedMVPView.getContext().getString(R.string.label_something_went_wrong));
        }
    }
}
