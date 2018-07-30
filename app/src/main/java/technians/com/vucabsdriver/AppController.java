package technians.com.vucabsdriver;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import technians.com.vucabsdriver.PayUMoneyIntegrate.AppEnvironment;

/**
 * Created by vineet on 10/18/2017.
 */

public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();
    AppEnvironment appEnvironment;
    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    static public boolean uiInForeground = false;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        appEnvironment = AppEnvironment.PRODUCTION;

    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req)  {
//        req.setTag(TAG);
        req.setTag(TAG);
        getRequestQueue().add(req);


    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

}