package technians.com.vucabsdriver.View.Login.LoginWithPhone;

import technians.com.vucabsdriver.View.MVPView;

public interface LoginWithPhoneMVPView extends MVPView{

    void loaderror(int stringID);

    void gotoOTPActivity(String mobilenumber);

    void showMessage(String message);
}
