package technians.com.vucabsdriver.View.MainView.Fragments.MyDuty;

import io.realm.Realm;
import technians.com.vucabsdriver.View.MVPView;
import technians.com.vucabsdriver.model.PendingRequest.BookingData;

public interface MyDutyMVPView extends MVPView {
    void setData(BookingData bookingData);

    Realm getRealm();

    void nobooking();
}