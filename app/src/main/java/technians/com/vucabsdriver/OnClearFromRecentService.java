package technians.com.vucabsdriver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import technians.com.vucabsdriver.Utilities.SessionManager;

public class OnClearFromRecentService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        //Code here
        SessionManager sessionManager = new SessionManager(OnClearFromRecentService.this);
        if (sessionManager.getDriverStatus() == 1) {
            FirebaseDatabase.getInstance().getReference().child("driver_current_location").child(String.valueOf(sessionManager.getDriverId())).removeValue();
            FirebaseDatabase.getInstance().getReference().child("geofire").child(String.valueOf(sessionManager.getDriverId())).removeValue();
        }
        stopSelf();
    }
}
