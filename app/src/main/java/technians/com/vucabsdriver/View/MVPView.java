package technians.com.vucabsdriver.View;

import android.content.Context;

import technians.com.vucabsdriver.Utilities.SessionManager;

public interface MVPView {
    Context getContext();
    SessionManager getSession();
    void showProgress();
    void hideProgress();
    void showApiError(String message);
}
