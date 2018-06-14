package technians.com.vucabsdriver.View.MainView.ContainerActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import io.realm.Realm;
import technians.com.vucabsdriver.BackgroundFusedLocation;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.Login.LoginWithPhone.LoginWithPhoneActivity;
import technians.com.vucabsdriver.View.MainView.Fragments.BookingHistory.BookingHistoryFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.DriverDocuments.DocumentsFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.MyDuty.MyDutyFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.Passes.MyPassFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.MyProfile.ProfileFragment;
import technians.com.vucabsdriver.View.MainView.Fragments.RatingFeedback.RatingFeedbackFragment;
import technians.com.vucabsdriver.Model.Profile.Profile;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NavigationMVPView {
    private TextView DriverName, DriverEmail;
    private Realm realm;
    private NavigationPresenter presenter;
    private SessionManager sessionManager;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        sessionManager = new SessionManager(this);
        presenter = new NavigationPresenter();
        presenter.attachView(this);


        Realm.init(this);
        realm = Realm.getDefaultInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
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
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(sessionManager.getCurrentFragment()!=R.id.nav_myduty){
                displaySelectedScreen(R.id.nav_myduty);
            }else {
                super.onBackPressed();
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
//                    realm.beginTransaction();
//                    realm.where(Profile.class).findFirst().deleteFromRealm();
//                    realm.commitTransaction();
                    startActivity(new Intent(NavigationActivity.this, LoginWithPhoneActivity.class));
                    stopService(new Intent(NavigationActivity.this, BackgroundFusedLocation.class));
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
        try {
            profile = realm.where(Profile.class).findFirst();
            DriverName.setText(profile.getName().toUpperCase());
            DriverEmail.setText(profile.getEmail());
            final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("driver_status").child(String.valueOf(profile.getDriver_ID()));
//
            mDatabase.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        int status = Integer.valueOf(dataSnapshot.getValue().toString());
                        Log.v("Nav123", "Status: " + dataSnapshot);

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
        }
        catch (Exception e){
            Toast.makeText(getContext(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }

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
        }catch (Exception e){
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
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case MyDutyFragment.REQUEST_CHECK_SETTINGS: {
                    MyDutyFragment fragment = (MyDutyFragment) getSupportFragmentManager().findFragmentByTag(getString(R.string.myduty));
                    fragment.startService();
                    break;
                }
            }
        }
    }
}
