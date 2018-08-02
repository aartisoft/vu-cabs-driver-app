package technians.com.vucabsdriver.View.MainView.AssingedBooking;

import io.realm.Realm;
import technians.com.vucabsdriver.View.MVPView;

public interface BookingAssingedMVPView extends MVPView {
    void dialogdismiss();

    Realm getRealm();

    void gotoTripCompleteActivity();

    void stopService();
}
