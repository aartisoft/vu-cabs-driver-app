package technians.com.vucabsdriver.View.MainView.ContainerActivity;

import io.realm.Realm;
import technians.com.vucabsdriver.View.MVPView;

public interface NavigationMVPView extends MVPView {
    Realm getRealm();

    void setProfileData();

    void getPushToken();
}
