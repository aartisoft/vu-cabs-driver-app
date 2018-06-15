package technians.com.vucabsdriver.View.MainView.Fragments.MyDuty;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import io.realm.Realm;
import technians.com.vucabsdriver.BackgroundFusedLocation;
import technians.com.vucabsdriver.CustomInfoWindow.CustomInfoWindowGoogleMap;
import technians.com.vucabsdriver.CustomInfoWindow.InfoWindowData;
import technians.com.vucabsdriver.CustomToggleButton.CustomToggleButton;
import technians.com.vucabsdriver.Model.DriverLocationPackage.DriverCurrentLocation;
import technians.com.vucabsdriver.Model.DriverLocationPackage.DriverLocation;
import technians.com.vucabsdriver.Model.DriverLocationPackage.ResumeMap;
import technians.com.vucabsdriver.Model.PendingRequest.BookingData;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.MainView.BookingOTP.OTPBookingActivity;


public class MyDutyFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, MyDutyMVPView {

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 858;
    public static final int REQUEST_CHECK_SETTINGS = 100;

    private GoogleMap mMap;
    private Location userLocation;

    private CustomToggleButton customToggleButton;
    private InfoWindowData info = new InfoWindowData();
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    private MyDutyPresenter presenter;
    private Realm realm;
    private SupportMapFragment mapFragment;

    private  TextView BookingId, BookingDate, Destination, NoBooking;
    private BookingData bookingData;
    private LinearLayout linearLayout_booking;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_duty, container, false);
        progressDialog = Constants.showProgressDialog(getActivity());
        presenter = new MyDutyPresenter();
        presenter.attachView(this);
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sessionManager = new SessionManager(getActivity());


        customToggleButton = view.findViewById(R.id.fragment_myduty_togglebtn);
        ImageView refreshbtn = view.findViewById(R.id.fragment_myduty_btn_refresh);
        BookingId = view.findViewById(R.id.fragment_myduty_tv_bookingid);
        BookingDate = view.findViewById(R.id.fragment_myduty_tv_date);
        Destination = view.findViewById(R.id.fragment_myduty_tv_destination);
        NoBooking = view.findViewById(R.id.fragment_myduty_tv_nobooking);
        Button btn_PickCustomer = view.findViewById(R.id.fragment_myduty_btn_pickcutomer);
        Button btn_starttrip = view.findViewById(R.id.fragment_myduty_btn_starttrip);
        linearLayout_booking = view.findViewById(R.id.fragment_myduty_layoutbooking);
        refreshbtn.setOnClickListener(this);
        btn_PickCustomer.setOnClickListener(this);
        btn_starttrip.setOnClickListener(this);

        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(BackgroundFusedLocation.BROADCAST_ACTION));
        if (String.valueOf(realm.where(ResumeMap.class).findFirst()).equals("null")) {
            startService();
        }


        customToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    startService();
                    changedriverstatus(true);
                    Toast.makeText(getActivity(), getString(R.string.online), Toast.LENGTH_LONG).show();
                } else {
                    stopService();
                    changedriverstatus(false);
                    Toast.makeText(getActivity(), getString(R.string.offline), Toast.LENGTH_LONG).show();
                }
            }
        });
        presenter.loadpendingrequest();
        return view;
    }

    private void changedriverstatus(Boolean status) {
        try {
            if (!String.valueOf(realm.where(ResumeMap.class).findFirst()).equals("null")) {
                realm.beginTransaction();
                realm.where(ResumeMap.class).findFirst().setDriverStatus(status);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMaxZoomPreference(20);

        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                getActivity(), R.raw.maps_style);
        googleMap.setMapStyle(style);
        if (checkPermission()) onLocationPermissionGranted();

    }


    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    private void onLocationPermissionGranted() {

        if (!checkPermission()) return;

        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMyLocationEnabled(true);

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                try {
                    if (!String.valueOf(realm.where(ResumeMap.class).findFirst()).equals("null")) {
//
                        ResumeMap resumeMap = realm.where(ResumeMap.class).findFirst();
                        if (resumeMap.getDriverStatus()) {
                            startService();
                        } else {
                            stopService();
                        }
//
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(),
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                try {
                    if (!String.valueOf(realm.where(ResumeMap.class).findFirst()).equals("null")) {

                        ResumeMap resumeMap = realm.where(ResumeMap.class).findFirst();
                        if (resumeMap.getDriverStatus() == null) {
                            customToggleButton.setChecked(true);
                        } else {
                            customToggleButton.setChecked(resumeMap.getDriverStatus());
                        }
//
                        CameraPosition cameraPosition = new CameraPosition.Builder()
                                .target(new LatLng(resumeMap.getLatitude(), resumeMap.getLongitude()))
                                .zoom(17)
                                .build();

                        addOverlay(new LatLng(resumeMap.getLatitude(), resumeMap.getLongitude()));
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        MarkerOptions markerOpt = new MarkerOptions();
                        markerOpt.position(new LatLng(resumeMap.getLatitude(), resumeMap.getLongitude()))
                                .snippet(resumeMap.getAddress())
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                        info.setLast_updated(resumeMap.getLastupdated());

                        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
                        mMap.setInfoWindowAdapter(customInfoWindow);

                        Marker marker = mMap.addMarker(markerOpt);
                        marker.setTag(info);
                        marker.showInfoWindow();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });

//
    }

    public void startService() {
        getActivity().getApplicationContext().startService(new Intent(getActivity(), BackgroundFusedLocation.class));

    }

    public void stopService() {
        getActivity().getApplicationContext().stopService(new Intent(getActivity(), BackgroundFusedLocation.class));

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                mMap.clear();
                Location location = intent.getParcelableExtra("Location");
                DriverCurrentLocation driverLocation = (DriverCurrentLocation) intent.getSerializableExtra("DriverLocation");
                userLocation = location;

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(new DriverLocation(1, location.getLatitude(), location.getLongitude()));
                realm.commitTransaction();

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()))
                        .zoom(17)
                        .build();

                addOverlay(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()));
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                addMarker(driverLocation);
                ResumeMap resumeMap = new ResumeMap(1, driverLocation.getAddress(), driverLocation.getUpdated_at(), true,
                        location.getLatitude(), location.getLongitude());
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(resumeMap);
                realm.commitTransaction();
            } catch (Exception e) {
                Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean checkPermission() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Ask for the permission
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            Toast.makeText(getActivity(), "Please give location permission", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (checkPermission()) {
                onLocationPermissionGranted();
            } else {
                getActivity().finishAffinity();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void addMarker(DriverCurrentLocation driverLocation) {
        try {
            MarkerOptions markerOpt = new MarkerOptions();
            markerOpt.position(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()))
                    .snippet(driverLocation.getAddress())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            info.setLast_updated(driverLocation.getUpdated_at());
//        info.setStatus(DriverStatus);

            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getActivity());
            mMap.setInfoWindowAdapter(customInfoWindow);

            Marker marker = mMap.addMarker(markerOpt);
            marker.setTag(info);
            marker.showInfoWindow();
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void addOverlay(LatLng place) {

        GroundOverlay groundOverlay = mMap.addGroundOverlay(new
                GroundOverlayOptions()
                .position(place, 100)
                .transparency(0.5f)
                .zIndex(3)
                .image(BitmapDescriptorFactory.fromBitmap(drawableToBitmap(getActivity().getDrawable(R.drawable.map_overlay)))));

        startOverlayAnimation(groundOverlay);
    }


    private void startOverlayAnimation(final GroundOverlay groundOverlay) {

        AnimatorSet animatorSet = new AnimatorSet();

        ValueAnimator vAnimator = ValueAnimator.ofInt(0, 100);
        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
        vAnimator.setRepeatMode(ValueAnimator.RESTART);
        vAnimator.setInterpolator(new LinearInterpolator());
        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                final Integer val = (Integer) valueAnimator.getAnimatedValue();
                groundOverlay.setDimensions(val);

            }
        });

        ValueAnimator tAnimator = ValueAnimator.ofFloat(0, 1);
        tAnimator.setRepeatCount(ValueAnimator.INFINITE);
        tAnimator.setRepeatMode(ValueAnimator.RESTART);
        tAnimator.setInterpolator(new LinearInterpolator());
        tAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float val = (Float) valueAnimator.getAnimatedValue();
                groundOverlay.setTransparency(val);
            }
        });

        animatorSet.setDuration(3000);
        animatorSet.playTogether(vAnimator, tAnimator);
        animatorSet.start();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(getString(R.string.myduty));
    }


    @Override
    public void onDestroyView() {
        realm.close();
        presenter.detachView();
        getActivity().unregisterReceiver(broadcastReceiver);
        super.onDestroyView();
    }


    @Override
    public SessionManager getSession() {
        return sessionManager;
    }

    @Override
    public void showProgress() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    @Override
    public void showApiError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_myduty_btn_refresh: {
                presenter.loadpendingrequest();
                break;
            }
            case R.id.fragment_myduty_btn_pickcutomer: {
                try {
                    DriverLocation driverLocation = realm.where(DriverLocation.class).findFirst();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + driverLocation.getLatitude() + "," + driverLocation.getLongitude() +
                                    "&daddr=" + bookingData.getPickup_latitude() + "," + bookingData.getPickup_longitude() + ""));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.fragment_myduty_btn_starttrip: {
                startActivity(new Intent(getActivity(), OTPBookingActivity.class).putExtra("ID", bookingData.getId()));
                break;
            }
        }
    }

    @Override
    public void setData(BookingData bookingData) {
        try {
            this.bookingData = bookingData;
            NoBooking.setVisibility(View.GONE);
            linearLayout_booking.setVisibility(View.VISIBLE);
            BookingId.setText(String.valueOf(bookingData.getId()));
            Destination.setText(bookingData.getDrop_location());
            BookingDate.setText(Constants.formateDateFromstring("mm-dd-yyyy", "dd.MMM.yyyy hh:mm aaa", bookingData.getDate()));
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Realm getRealm() {
        return realm;
    }


    @Override
    public void nobooking() {
        NoBooking.setVisibility(View.VISIBLE);
        linearLayout_booking.setVisibility(View.GONE);
    }
}
