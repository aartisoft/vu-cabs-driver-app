package technians.com.vucabsdriver;

import android.app.Application;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import technians.com.vucabsdriver.PayUMoneyIntegrate.AppEnvironment;

/**
 * Created by vineet on 10/18/2017.
 */

public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();

    private OkHttpClient okHttpClient;
    private static AppController mInstance;
    AppEnvironment appEnvironment;

    private RequestQueue mRequestQueue;
    static public boolean uiInForeground = false;
    @Override
    public void onCreate() {
        super.onCreate();

        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(100, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS).
                build();
        mInstance = this;

        appEnvironment = AppEnvironment.SANDBOX;

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

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }


//    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener)
//    {
//        ConnectivityReceiver.connectivityReceiverListener = listener;
//    }

    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }
}