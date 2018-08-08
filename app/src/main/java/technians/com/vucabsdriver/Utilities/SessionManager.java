package technians.com.vucabsdriver.Utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by vineet on 9/25/2017.
 */

public class SessionManager {
    private static final String KEY_REQUEST_UPDATES ="request_updates" ;
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    // Shared preferences file name
    private static final String PREF_NAME = "VUCABSDRIVER";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_PUSH_TOKEN = "push_token";
    private static final String KEY_DRIVER_STATUS = "status";
    private static final String KEY_CURRENT_FRAGMENT = "current_fragment";
    private static final String KEY_DRIVER_ID = "driver_id";
    private static final String KEY_DRIVING_ACTIVE = "driver_active";
    private static final String KEY_RIDE_DISTANCE = "driver_distance";
    private static final String KEY_RIDE_TIME = "driver_time";
    private static final String KEY_PASSES_COUNT= "passescount";

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

    public void setDriverStatus(int driverStatus) {
        editor.putInt(KEY_DRIVER_STATUS, driverStatus);
        // commit changes
        editor.commit();
    }
    public void setRequestUpdates(Boolean requestUpdates) {
        editor.putBoolean(KEY_REQUEST_UPDATES, requestUpdates);
        // commit changes
        editor.commit();
    }
    public void setPassesCount(int passesCount) {
        editor.putInt(KEY_PASSES_COUNT, passesCount);
        // commit changes
        editor.commit();
    }

    public void setDriverId(int driverId) {
        editor.putInt(KEY_DRIVER_ID, driverId);
        // commit changes
        editor.commit();
    }

    public void setDrivingActive(Boolean active) {
        editor.putBoolean(KEY_DRIVING_ACTIVE, active);
        // commit changes
        editor.commit();
    }

    public void setRideDistance(String Distance) {
        editor.putString(KEY_RIDE_DISTANCE, Distance);
        // commit changes
        editor.commit();
    }
//    public void setRidestartTime(Date date) {
//        editor.putString(KEY_RIDE_TIME, date);
//        // commit changes
//        editor.commit();
//    }



    public boolean isDriving() {
        return pref.getBoolean(KEY_DRIVING_ACTIVE, false);
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

    public int getDriverStatus() {
        return pref.getInt(KEY_DRIVER_STATUS, -1);
    }

    public int getDriverId() {
        return pref.getInt(KEY_DRIVER_ID, -1);
    }

    public String getRideDistance() {
        return pref.getString(KEY_RIDE_DISTANCE,"0.0");
    }

    public int getRideTime() {
        return pref.getInt(KEY_RIDE_TIME, -1);
    }
    public int getPassesCount() {
        return pref.getInt(KEY_PASSES_COUNT, -1);
    }
    public Boolean getRequestUpdateStatus() {
        return pref.getBoolean(KEY_REQUEST_UPDATES, true);
    }
}