package technians.com.vucabsdriver.View.MainView.Fragments.Passes;

import io.realm.Realm;
import technians.com.vucabsdriver.View.MVPView;

public interface MyPassesMVPView extends MVPView{
    Realm getRealm();

    void setdata(String ridesLeft, String totalRides, String rideAmount);
}
