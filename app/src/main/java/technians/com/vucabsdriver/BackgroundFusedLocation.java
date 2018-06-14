package technians.com.vucabsdriver;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import technians.com.vucabsdriver.Model.DriverLocationPackage.DriverCurrentLocation;
import technians.com.vucabsdriver.Model.Profile.Profile;

import static technians.com.vucabsdriver.Utilities.Constants.getLocationAddress;


public class BackgroundFusedLocation extends Service {
    public static final String BROADCAST_ACTION = "technians.com.vucabsdriver.displayevent";
    Intent intent;

    public BackgroundFusedLocation() {
    }

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    // Write

    long UPDATE_INTERVAL = 5000;
    long FASTEST_INTERVAL = 5000;
    int SMALLEST_DISPLACEMENT = 10;
    String DriverId;
    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback mLocationCallback;
    private Realm realm;
    Profile profile;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        startLocationUpdates();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    protected void startLocationUpdates() {
        profile = realm.where(Profile.class).findFirst();
        if (profile!=null) {
            try {
                DriverId = String.valueOf(profile.getDriver_ID());
                final LocationRequest mLocationRequest = new LocationRequest();
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
                mLocationRequest.setInterval(UPDATE_INTERVAL);
                mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

                mLocationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());

                    }
                };
                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
                builder.addLocationRequest(mLocationRequest);

                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                connectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean connected = snapshot.getValue(Boolean.class);
                        if (connected) {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                                    Looper.myLooper());

                        } else {
                            mDatabase.child(getString(R.string.firebasenode))
                                    .child(DriverId).onDisconnect().removeValue();
                            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            }catch (Exception e) {
                Toast.makeText(this, getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onLocationChanged(Location location) {
        try {
            if (location.getAccuracy() < 100.0 && location.getSpeed() < 6.95) {

                String Address =
                        getLocationAddress(location.getLatitude(), location.getLongitude(), BackgroundFusedLocation.this);
                Date currenttime = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MMM.yyyy hh:mm aaa");
                String updatedat = formatter.format(currenttime);
                DriverCurrentLocation driverLocation = new DriverCurrentLocation(Address, updatedat, profile.getCar_Type()
                        , profile.getDriver_ID(), profile.getDriver_Status(), location.getLatitude(), location.getLongitude());
                mDatabase.child(getString(R.string.firebasenode))
                        .child(DriverId)
                        .setValue(driverLocation);
                sendDataToActivity(location, driverLocation);
            }
        }catch (Exception e) {
            Toast.makeText(this, getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendDataToActivity(Location location, DriverCurrentLocation driverLocation) {
        intent.putExtra("Location", location);
        intent.putExtra("DriverLocation", driverLocation);
        sendBroadcast(intent);
    }


    @Override
    public void onDestroy() {
        realm.close();
        mDatabase.child(getString(R.string.firebasenode))
                .child(DriverId).removeValue();
        fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        stopSelf();
        super.onDestroy();
    }
}