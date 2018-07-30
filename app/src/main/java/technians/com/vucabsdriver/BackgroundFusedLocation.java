package technians.com.vucabsdriver;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
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
import technians.com.vucabsdriver.Utilities.SessionManager;

import static technians.com.vucabsdriver.Utilities.Constants.getLocationAddress;


public class BackgroundFusedLocation extends Service {
    public static final String BROADCAST_ACTION = "technians.com.vucabsdriver.displayevent";
    private static final String GEO_FIRE_DB = "https://vucabsdriverapp.firebaseio.com";
    private static final String GEO_FIRE_REF = GEO_FIRE_DB + "/geofire";


    public BackgroundFusedLocation() {
    }

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private String DriverId;
    private Intent intent;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback mLocationCallback;
    private Realm realm;
    private Profile profile;
    private GeoFire geoFire;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("NavigationActivity","Service started");
        RealmController1 realmController1 = new RealmController1(this);
        realm= Realm.getInstance(realmController1.initializeDB());
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        geoFire = new GeoFire(FirebaseDatabase.getInstance().getReferenceFromUrl(GEO_FIRE_REF));
        startLocationUpdates();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    protected void startLocationUpdates() {
        Log.v("NavigationActivity","startLocationUpdates");
        Log.v("NavigationActivity","Profile: "+realm.where(Profile.class).findFirst());
        profile = realm.where(Profile.class).findFirst();
        if (profile != null) {
            try {
                DriverId = String.valueOf(profile.getDriver_ID());
                final LocationRequest mLocationRequest = new LocationRequest();
                mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                int SMALLEST_DISPLACEMENT = 10;
                mLocationRequest.setSmallestDisplacement(SMALLEST_DISPLACEMENT);
                long UPDATE_INTERVAL = 5000;
                mLocationRequest.setInterval(UPDATE_INTERVAL);
                long FASTEST_INTERVAL = 5000;
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
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean connected = snapshot.getValue(Boolean.class);
                        if (connected) {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback,
                                    Looper.myLooper());
                            Log.v("NavigationActivity","Connected");

                        } else {
                            mDatabase.child(getString(R.string.firebasenode))
                                    .child(DriverId).onDisconnect().removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    Log.v("Background123", "complete");
                                }
                            });
                            geoFire.removeLocation(DriverId, new GeoFire.CompletionListener() {
                                @Override
                                public void onComplete(String key, DatabaseError error) {
                                    Log.v("Background123", "Key: " + key);
                                    Log.v("Background123", "Error: " + error);
                                }
                            });
                            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
                            Log.v("NavigationActivity","Not connected");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
            } catch (Exception e) {
                Toast.makeText(this, getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onLocationChanged(Location location) {
        Log.v("NavigationActivity","OnLocation Change");
        try {
            if (location.getAccuracy() < 100.0 && location.getSpeed() < 6.95) {

                String Address = getLocationAddress(location.getLatitude(), location.getLongitude(), BackgroundFusedLocation.this);
                Date currenttime = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MMM.yyyy hh:mm aaa");
                String updatedat = formatter.format(currenttime);
                DriverCurrentLocation driverLocation = new DriverCurrentLocation(Address, updatedat, profile.getCar_Type(),
                        profile.getDriver_ID(), new SessionManager(BackgroundFusedLocation.this).getDriverStatus(), location.getLatitude(),
                        location.getLongitude(), profile.getCar_Seat(), profile.getName(), profile.getMobileNumber(),
                        profile.getProfileURL(), profile.getCar_Name(), profile.getCar_Number(), profile.getCarURL(),0);
                mDatabase.child(getString(R.string.firebasenode))
                        .child(DriverId)
                        .setValue(driverLocation);
                geoFire.setLocation(DriverId, new GeoLocation(location.getLatitude(), location.getLongitude()), new GeoFire.CompletionListener() {
                    //            @Override
                    public void onComplete(String key, DatabaseError error) {
                        if (error != null) {
                            Log.v("GeoQuery", "onComplete There was an error saving the location to GeoFire: " + error);
                        } else {
                            Log.v("GeoQuery", "onComplete Location saved on server successfully!");
                        }
                    }
                });
                sendDataToActivity(location, driverLocation);
            }
        } catch (Exception e) {
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
        Log.v("Background123", "OnDestroy");
        realm.close();

        try {
            mDatabase.child(getString(R.string.firebasenode))
                    .child(DriverId).removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    Log.v("Background123", "Node removed");
                }
            });
            geoFire.removeLocation(DriverId, new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    Log.v("Background123", "Key: " + key);
                    Log.v("Background123", "Error: " + error);
                }
            });
        }catch (Exception e){

        }
        fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
        stopSelf();
        super.onDestroy();
    }
}