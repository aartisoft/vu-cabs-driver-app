package technians.com.vucabsdriver.View.MainView.ContainerActivity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import io.realm.Realm;
import technians.com.vucabsdriver.BackgroundFusedLocation;
import technians.com.vucabsdriver.Model.DriverLocationPackage.ResumeMap;
import technians.com.vucabsdriver.Model.Profile.Profile;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.RealmController1;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.Login.LoginWithPhone.LoginWithPhoneActivity;
import technians.com.vucabsdriver.View.MainView.Fragments.BookingHistory.BookingHistoryFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.DriverDocuments.DocumentsFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.MyDuty.MyDutyFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.MyProfile.ProfileFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.Passes.MyPassFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.RatingFeedback.RatingFeedbackFragment;

import static technians.com.vucabsdriver.FirebaseService.MyFirebaseMessagingService.BROADCAST_ACTION;
import static technians.com.vucabsdriver.View.MainView.Fragments.MyDuty.MyDutyFragment.LOCATION_PERMISSION_REQUEST_CODE;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NavigationMVPView {
    private TextView DriverName, DriverEmail;
    private Realm realm;
    private NavigationPresenter presenter;
    private SessionManager sessionManager;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private Profile profile;
    LocationSettingsRequest mLocationSettingsRequest;
    LocationManager manager;
    public static final int REQUEST_CHECK_SETTINGS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        sessionManager = new SessionManager(this);
        presenter = new NavigationPresenter();
        presenter.attachView(this);


        RealmController1 realmController1 = new RealmController1(this);
        realm = Realm.getInstance(realmController1.initializeDB());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        DriverName = headerView.findViewById(R.id.header_tv_name);
        DriverEmail = headerView.findViewById(R.id.header_tv_email);

        presenter.setHeaderdata();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        sessionManager.setCurrentFragment(R.id.nav_myduty);
        if (checkPermission()) {
            Log.v("VUCABSDRIVER", "checkPermission: true");
            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                checkgpsstatus();
            } else {
                Log.v("VUCABSDRIVER", "checkPermission: else");
//                startService(new Intent(NavigationActivity.this, BackgroundFusedLocation.class));
                if (!String.valueOf(realm.where(ResumeMap.class).findFirst()).equals("null")) {
                    Log.v("VUCABSDRIVER", "checkPermission: resume map");
                    ResumeMap resumeMap = realm.where(ResumeMap.class).findFirst();
                    Log.v("VUCABSDRIVER", "checkPermission: resume map status: " + resumeMap.getDriverStatus());
                    if (!resumeMap.getDriverStatus()) {
                        stopService(new Intent(NavigationActivity.this, BackgroundFusedLocation.class));
                    } else {
                        startService(new Intent(NavigationActivity.this, BackgroundFusedLocation.class));
                    }
                }

            }
        }
    }

    private void checkgpsstatus() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
//
        SettingsClient client = LocationServices.getSettingsClient(NavigationActivity.this);
        client
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.v("VUCABSDRIVER", "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(NavigationActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.v("VUCABSDRIVER", "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";

                                Toast.makeText(NavigationActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                    }
                });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (sessionManager.getCurrentFragment() != R.id.nav_myduty) {
                displaySelectedScreen(R.id.nav_myduty);
            } else {
                super.onBackPressed();
            }
        }


    }

    public boolean checkPermission() {
        Log.v("VUCABSDRIVER", "checkPermission");
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            if (String.valueOf(realm.where(ResumeMap.class).findFirst()).equals("null")) {
                startService(new Intent(NavigationActivity.this, BackgroundFusedLocation.class));
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.v("VUCABSDRIVER", "onRequestPermissionsResult");
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (String.valueOf(realm.where(ResumeMap.class).findFirst()).equals("null")) {
                    startService(new Intent(NavigationActivity.this, BackgroundFusedLocation.class));
                }
                Log.v("VUCABSDRIVER", "PERMISSION Granted");
            } else {
                final MaterialStyledDialog.Builder dialogHeader_1 = new MaterialStyledDialog.Builder(NavigationActivity.this)
                        .setIcon(R.drawable.ic_if_setting)
                        .withDialogAnimation(true)
                        .withIconAnimation(true)
                        .setHeaderColorInt(getResources().getColor(R.color.colorAccent))
                        .setDescription(getString(R.string.allowLocation))
                        .setPositiveText(getString(R.string.action_settings))
                        .setNegativeText(getString(R.string.cb_b_cancel))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finishAffinity();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                            }
                        });
                dialogHeader_1.show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        realm.close();
        this.stopService(new Intent(NavigationActivity.this, BackgroundFusedLocation.class));
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);

        return true;
    }


    public void displaySelectedScreen(int id) {
        //creating fragment object
        Fragment fragment = null;
        String tag = "";
        sessionManager.setCurrentFragment(id);
        //initializing the fragment object which is selected
        switch (id) {
            case R.id.nav_myduty:
                fragment = new MyDutyFragment();
                tag = getString(R.string.myduty);
                break;
            case R.id.nav_mypasses:
                fragment = new MyPassFragment();
                break;
            case R.id.nav_documents:
                fragment = new DocumentsFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_ridehistory:
                fragment = new BookingHistoryFragment();
                break;
            case R.id.nav_ratingfeedback:
                fragment = new RatingFeedbackFragment();
                break;
            case R.id.nav_logout:
                sessionManager.setLogin(false);
                stopService(new Intent(NavigationActivity.this, BackgroundFusedLocation.class));
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
                startActivity(new Intent(NavigationActivity.this, LoginWithPhoneActivity.class));
                finish();
                break;
            case R.id.nav_termscons:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_terms)));
                startActivity(browserIntent);
                break;
        }

        //replacing the fragment
        if (fragment != null) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contentframe, fragment, tag);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public SessionManager getSession() {
        return sessionManager;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showApiError(String message) {

    }

    @Override
    public Realm getRealm() {
        return realm;
    }

    @Override
    public void setProfileData() {
        Log.v("VUCABSDRIVER", "setProfileData");
        try {
            profile = realm.where(Profile.class).findFirst();
            DriverName.setText(profile.getName().toUpperCase());
            DriverEmail.setText(profile.getEmail());
            sessionManager.setDriverId(profile.getDriver_ID());
            Intent intent = new Intent(BROADCAST_ACTION);
            sendBroadcast(intent);

            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                    .child("driver_status").child(String.valueOf(profile.getDriver_ID()));

            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        int status = Integer.valueOf(dataSnapshot.getValue().toString());

                        if (status == 0) {
                            if (!realm.isClosed()) {
                                realm.beginTransaction();
                                profile.setDriver_Status(0);
                                realm.commitTransaction();
                                presenter.createNotification();
                                new SessionManager(NavigationActivity.this).setLogin(false);
                                finishAffinity();
                            }
                        } else if (status == 1) {
                            if (!realm.isClosed()) {
                                realm.beginTransaction();
                                profile.setDriver_Status(1);
                                realm.commitTransaction();
                            }
                        }
                    } else {
                        mDatabase.setValue(profile.getDriver_Status());
                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }

        final DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference()
                .child("driver_current_location").child(String.valueOf(profile.getDriver_ID())).child("status");
        mDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    sessionManager.setDriverStatus(Integer.valueOf(dataSnapshot.getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void getPushToken() {
        try {
            profile = realm.where(Profile.class).findFirst();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Create channel to show notifications.
                String channelId = getString(R.string.default_notification_channel_id);
                String channelName = getString(R.string.default_notification_channel_name);
                NotificationManager notificationManager =
                        getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                        channelName, NotificationManager.IMPORTANCE_LOW));
            }


            String token = FirebaseInstanceId.getInstance().getToken();
            sessionManager.setpushToken(token);

            mDatabase.child(getString(R.string.firebasepushnode))
                    .child(String.valueOf(profile.getDriver_ID()))
                    .setValue(token);

//        String msg = getString(R.string.msg_token_fmt, token);
//        Log.v(TAG, msg);
        } catch (Exception e) {
            Toast.makeText(getContext(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displaySelectedScreen(sessionManager.getCurrentFragment());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check if the request code is same as what is passed  here it is 2
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case RESULT_OK: {
                    MyDutyFragment fragment = (MyDutyFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.myduty));
                    fragment.startService();
                    Log.v("VUCABSDRIVER", "REQUEST OK");
                    break;
                }
                case RESULT_CANCELED: {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    Log.v("VUCABSDRIVER", "REQUEST CAncel");
//                    finishAffinity();
                    break;
                }
            }
        }
    }
}
