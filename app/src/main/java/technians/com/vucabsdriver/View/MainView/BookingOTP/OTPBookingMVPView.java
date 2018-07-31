package technians.com.vucabsdriver.View.MainView.BookingOTP;

import io.realm.Realm;
import technians.com.vucabsdriver.View.MVPView;

public interface OTPBookingMVPView extends MVPView{
    Realm getRealm();

    void gotoAssingedbooking();

    void showMessage(String message);

    void startService();
}
