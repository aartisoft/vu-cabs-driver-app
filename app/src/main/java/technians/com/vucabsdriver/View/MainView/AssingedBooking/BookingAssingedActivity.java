package technians.com.vucabsdriver.View.MainView.AssingedBooking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import technians.com.vucabsdriver.Model.DriverLocationPackage.DriverLocation;
import technians.com.vucabsdriver.Model.PendingRequest.BookingData;
import technians.com.vucabsdriver.Model.Profile.Profile;
import technians.com.vucabsdriver.R;
import technians.com.vucabsdriver.RealmController1;
import technians.com.vucabsdriver.Utilities.Constants;
import technians.com.vucabsdriver.Utilities.SessionManager;
import technians.com.vucabsdriver.View.MainView.TripEnd.TripEndActivity;

public class BookingAssingedActivity extends AppCompatActivity implements View.OnClickListener, BookingAssingedMVPView {

    private Realm realm;
    private int ID;

    private ProgressDialog progressDialog;

    private BookingAssingedPresenter presenter;
    private SessionManager sessionManager;
    private Profile profile;
    private BookingData bookingData;
    private DriverLocation driverLocation;
    private AlertDialog b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_assinged);
        progressDialog = Constants.showProgressDialog(this);
        sessionManager = new SessionManager(this);
        ID = getIntent().getIntExtra("ID", ID);
        RealmController1 realmController1 = new RealmController1(this);
        realm= Realm.getInstance(realmController1.initializeDB());

        presenter = new BookingAssingedPresenter();
        presenter.attachView(this);
        Toolbar toolbar = findViewById(R.id.activity_bookingassinged_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(getString(R.string.assingedtrip));

        TextView mTextViewCustomerName = findViewById(R.id.activity_bookingassinged_custname);
        TextView mTextViewBookingID = findViewById(R.id.activity_bookingassinged_bookingid);
        TextView mTextViewPickLocation = findViewById(R.id.activity_bookingassinged_pickLocation);
        TextView mTextViewDropLocation = findViewById(R.id.activity_bookingassinged_dropLocation);
        TextView mTextViewPaymentType = findViewById(R.id.activity_bookingassinged_payment);
        TextView mTextViewDate = findViewById(R.id.activity_bookingassinged_date);
        TextView mTextViewTime = findViewById(R.id.activity_bookingassinged_time);
        Button mButtonNavigate = findViewById(R.id.activity_bookingassinged_btn_startnavigation);
        Button mButtonAddToll = findViewById(R.id.activity_bookingassinged_btn_addtoll);
        Button mButtonEndTrip = findViewById(R.id.activity_bookingassinged_btn_endtrip);
        mButtonNavigate.setOnClickListener(this);
        mButtonAddToll.setOnClickListener(this);
        mButtonEndTrip.setOnClickListener(this);

        try {
            profile = realm.where(Profile.class).findFirst();
            driverLocation = realm.where(DriverLocation.class).findFirst();
            bookingData = realm.where(BookingData.class).equalTo("id", ID).findFirst();
            mTextViewCustomerName.setText(bookingData.getCustomer_name());
            mTextViewBookingID.setText("BOOKING ID: " + bookingData.getId());
            mTextViewPickLocation.setText(bookingData.getPickup_location());
            mTextViewDropLocation.setText(bookingData.getDrop_location());
            String upperString = bookingData.getPayment_type().substring(0, 1).toUpperCase() + bookingData.getPayment_type().substring(1);
            mTextViewPaymentType.setText(upperString);
            mTextViewDate.setText(Constants.formateDateFromstring("mm-dd-yyyy", "dd.MMM.yyyy", bookingData.getDate()));
            mTextViewTime.setText(Constants.formateDateFromstring("mm-dd-yyyy", "hh:mm aaa", bookingData.getDate()));
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        realm.close();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_bookingassinged_btn_startnavigation: {
                try {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + bookingData.getPickup_latitude() + "," + bookingData.getPickup_longitude() +
                                    "&daddr=" + bookingData.getDrop_latitude() + "," + bookingData.getDrop_longitude() + ""));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.activity_bookingassinged_btn_addtoll: {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(BookingAssingedActivity.this);
                LayoutInflater inflater = LayoutInflater.from(BookingAssingedActivity.this);
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                dialogBuilder.setView(dialogView);
                final EditText TollName = dialogView.findViewById(R.id.et_tollname);
                final EditText TollPrice = dialogView.findViewById(R.id.et_tollprice);
                final TextInputLayout TIL_TollName = dialogView.findViewById(R.id.til_tollname);
                final TextInputLayout TIL_TollPrice = dialogView.findViewById(R.id.til_tollprice);
                dialogBuilder.setCancelable(false);
                dialogBuilder.setTitle("Add toll");
                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    public void onClick(final DialogInterface dialog, int whichButton) {

                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                b = dialogBuilder.create();
                b.show();
                b.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            boolean failFlag = false;
                            CharSequence error = getString(R.string.madatoryfield);
                            if (TollName.getText().toString().equals("")) {
                                failFlag = true;
                                TIL_TollName.setError(error);
                            } else {
                                TIL_TollName.setErrorEnabled(false);
                            }
                            if (TollPrice.getText().toString().equals("")) {
                                failFlag = true;
                                TIL_TollPrice.setError(error);
                            } else {
                                TIL_TollPrice.setErrorEnabled(false);
                            }
                            if (!failFlag) {
                                presenter.Addtoll(profile, driverLocation, bookingData, TollName.getText().toString(), TollPrice.getText().toString());
                            }
                        } catch (Exception e) {
                            Toast.makeText(getContext(), getString(R.string.label_something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }
            case R.id.activity_bookingassinged_btn_endtrip: {
                presenter.endTrip(profile, driverLocation, bookingData);
                break;
            }
        }
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dialogdismiss() {
        b.dismiss();
        Toast.makeText(this, getString(R.string.tollsuccess), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Realm getRealm() {
        return realm;
    }

    @Override
    public void gotoTripCompleteActivity() {
        startActivity(new Intent(BookingAssingedActivity.this, TripEndActivity.class).putExtra("ID", ID));
    }
}
