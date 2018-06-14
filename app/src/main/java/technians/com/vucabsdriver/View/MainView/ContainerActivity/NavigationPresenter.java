package technians.com.vucabsdriver.View.MainView.ContainerActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import technians.com.vucabsdriver.Presenter.Presenter;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.Model.Profile.Profile;
import technians.com.vucabsdriver.Model.Profile.ProfileResponce;
import technians.com.vucabsdriver.Model.RetrofitError.NetworkError;
import technians.com.vucabsdriver.rest.ApiClient;
import technians.com.vucabsdriver.rest.ApiInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NavigationPresenter implements Presenter<NavigationMVPView> {
    private NavigationMVPView navigationMVPView;

    @Override
    public void attachView(NavigationMVPView view) {
        this.navigationMVPView = view;
    }

    @Override
    public void detachView() {
        this.navigationMVPView = null;
    }

    public void setHeaderdata() {
        Profile profile = navigationMVPView.getRealm().where(Profile.class).findFirst();
        if (profile == null) {
            getProfile();
        } else {
            navigationMVPView.setProfileData();
        }
    }

    public void getProfile() {

        try {
            navigationMVPView.showProgress();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ProfileResponce> call = apiService.getProfile(navigationMVPView.getSession().getToken());
            call.enqueue(new Callback<ProfileResponce>() {
                @Override
                public void onResponse(@NonNull Call<ProfileResponce> call, @NonNull Response<ProfileResponce> response) {
                    navigationMVPView.hideProgress();

                    if (response.body() != null) {
                        if (response.body().getStatus() == 200) {
                            Profile profile = response.body().getProfile();
                            Realm realm = navigationMVPView.getRealm();
                            realm.beginTransaction();
                            realm.deleteAll();
                            realm.copyToRealmOrUpdate(profile);
                            realm.commitTransaction();
                            navigationMVPView.getPushToken();
                            navigationMVPView.setProfileData();

                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ProfileResponce> call, @NonNull Throwable t) {
                    navigationMVPView.hideProgress();
                    String error = new NetworkError(t).getAppErrorMessage();
                    navigationMVPView.showApiError(error);
                }
            });
        }
        catch (Exception e){
            navigationMVPView.showApiError(navigationMVPView.getContext().getString(R.string.label_something_went_wrong));
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createNotification() {
        try {
            // define sound URI, the sound to be played when there's a notification
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            // intent triggered, you can add other intent for other actions
            Intent intent = new Intent(navigationMVPView.getContext(), NavigationActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(navigationMVPView.getContext(), 0, intent, 0);

            // this is it, we'll build the notification!
            // in the addAction method, if you don't want any icon, just set the first param to 0
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    navigationMVPView.getContext());

            Notification mNotification = mBuilder

                    .setContentTitle("DEACTIVATED!")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("You have been deactivated. To activate your services please contact VU Cabs support team!"))
                    .setContentText("You have been deactivated. To activate your services please contact VU Cabs support team!")
                    .setLargeIcon(BitmapFactory.decodeResource(navigationMVPView.getContext().getResources(), R.drawable.vucablogo_square_xxxdpi))
                    .setSmallIcon(R.drawable.vucablogo_square_xxxdpi)
                    .setContentIntent(pIntent)
                    .setSound(soundUri)

                    .addAction(R.drawable.ic_call_black_18dp, "Call", pIntent)
                    .addAction(R.drawable.ic_email_black_18dp, "Mail", pIntent)

                    .build();

            NotificationManager notificationManager = (NotificationManager)
                    navigationMVPView.getContext().getSystemService(NOTIFICATION_SERVICE);

            // If you want to hide the notification after it was selected, do the code below
            // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

            notificationManager.notify(0, mNotification);
        }
        catch (Exception e){
            navigationMVPView.showApiError(navigationMVPView.getContext().getString(R.string.label_something_went_wrong));
        }
    }


}
