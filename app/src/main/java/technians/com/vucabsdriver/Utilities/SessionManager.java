package technians.com.vucabsdriver.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vineet on 9/25/2017.
 */

public class SessionManager {
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // Shared preferences file name
    private static final String PREF_NAME = "VUCABSDRIVER";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_PUSH_TOKEN = "push_token";
    private static final String KEY_DRIVER_STATUS = "driver_status";
    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";

    public SessionManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setpushToken(String token) {
        editor.putString(KEY_PUSH_TOKEN, token);
        // commit changes
        editor.commit();
    }

    public void setLogin(boolean isLoggedIn, String token) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_TOKEN, token);
        // commit changes
        editor.commit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        // commit changes
        editor.commit();
    }

    public void setCurrentFragment(int nav_myduty) {
        editor.putInt(KEY_CURRENT_FRAGMENT, nav_myduty);
        // commit changes
        editor.commit();
    }


    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public String getToken() {
        return pref.getString(KEY_TOKEN, "");
    }

    public String getPushToken() {
        return pref.getString(KEY_PUSH_TOKEN, "");
    }

    public int getCurrentFragment() {
        return pref.getInt(KEY_CURRENT_FRAGMENT, -1);
    }
}