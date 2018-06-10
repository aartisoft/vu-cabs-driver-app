package technians.com.vucabsdriver.Utilities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vineet on 10/18/2017.
 */

//        android:fontFamily="sans-serif"           // roboto regular
//        android:fontFamily="sans-serif-light"     // roboto light
//        android:fontFamily="sans-serif-condensed" // roboto condensed
//        android:fontFamily="sans-serif-thin"      // roboto thin (android 4.2)
//        android:fontFamily="sans-serif-medium"    // roboto medium (android 5.0)

public class Constants {

    public static final String PayUMoneyHash= "https://www.vucabs.com/api/moneyhash-android";
    public static final  int PayUMoneyRequestCode= 2;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VUCAB_DRIVER";
    public static final String URL_lOGIN = "https://www.vucabs.com/api/driver/driver-login";
    public static final String URL_ENTEROTP = "https://www.vucabs.com/api/driver/otp";
    public static final String URL_DRIVERPROFILE = "https://www.vucabs.com/api/driver/get-details?token=";
    public static final String URL_ACTIVEDRIVERS = "https://www.vucabs.com/api/driver/active-driver?token=";
    public static final String URL_REACHARGECOUPON = "https://www.vucabs.com/api/driver/recharge?token=";
    public static final String URL_DOCUMENTLIST = "https://www.vucabs.com/api/driver/document-list?token=";
    public static final String URL_GETPASSES = "https://www.vucabs.com/api/driver/recharge-count?token=";
    public static final String URL_BOOKINGHISTORY = "https://www.vucabs.com/api/driver/driver-booking?token=";
    public static final String URL_TRIPCOMPLETE = "https://www.vucabs.com/api/driver/driver-ride?token=";
    public static final String URL_ENTERBOOKINGOTP = "https://www.vucabs.com/api/driver/booking-verify?token=";
    public static final String URL_ADDTOLL = "https://www.vucabs.com/api/driver/toll?token=";
    public static final String URL_RECHARGE_LIST = "https://www.vucabs.com/api/driver/driver-recharge-list?token=";

    //    -------------------------------------CONSTANT TAGS---------------------------------------
    public static final String TAG_LOGIN_REQ = "LOGIN_REQUEST";
    public static final String TAG_OTP_REQ = "OTP_REQUEST";
    public static final String TAG_DRIVERDOC_REQ = "DRIVERDOC_REQUEST";
    public static final String TAG_CHANGEDRIVER_REQ = "CHANGE_DRIVERDETAILS";
    public static final String TAG_GETPASSES_REQ = "GETPASSES_REQ";
    public static final String TAG_REACHARGECOUPON = "REACHARGECOUPON";
    public static final String TAG_BOOKINGOTP_REQ = "BOOKINGOTP";
    public static final String TAG_DRIVERPROFILE ="DRIVER_PROFILE" ;
    public static final String URL_TRACK_DRIVER = "https://www.vucabs.com/api/driver/driver-tracking?token=" ;
    public static final String TAG_TRACK_REQ = "TRACK_DRIVER" ;
    public static final String TAG_RECHARGE_PRICELIST_REQ ="RECHARGE_PRICELIST" ;



    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage("Please wait");
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        return m_Dialog;
    }
    public static ProgressDialog showProgressDialog(Context context,String message) {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        return m_Dialog;
    }

    public void createToken(Context context,String Cust_Token){
        SharedPreferences.Editor editor = context.getSharedPreferences("DRIVER_TOKEN", MODE_PRIVATE).edit();
        editor.putString("token", Cust_Token);
        editor.apply();
    }
    public String getToken(Context context)
    {
        SharedPreferences tokenpref = context.getSharedPreferences("DRIVER_TOKEN", MODE_PRIVATE);
        return tokenpref.getString("token","");
    }

    public void driverStatus(Context context,int status){
        SharedPreferences.Editor editor = context.getSharedPreferences("DRIVER_STATUS", MODE_PRIVATE).edit();
        editor.putInt("driver_status", status);
        editor.apply();
    }
    public int getDriverStatus(Context context)
    {
        SharedPreferences tokenpref = context.getSharedPreferences("DRIVER_STATUS", MODE_PRIVATE);
        return tokenpref.getInt("driver_status",3);
    }
    public static Toast showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }

    public static void ShowError(String error, Context context) {
        switch (error) {
            case "Check internet connection": {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("No Internet Connection")
                        .setMessage("It looks like your internet connection is off. Please turn it " +
                                "on and try again.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
                break;
            }
            case "Request timeout": {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Request timed out")
                        .setMessage("We couldn't connect to server. Check your internet connection and try again.")
                        .setCancelable(true)
                        .show();
                break;
            }
            case "Some error occurred":{
                Toast toast = Toast.makeText(context,"Some error occurred" , Toast.LENGTH_SHORT);
                toast.show();
                break;
            }
            default:{
                Toast toast = Toast.makeText(context,"Some error occurred" , Toast.LENGTH_SHORT);
                toast.show();
                break;
            }
        }

    }

    public static String FormatCarType(String s)
    {
        return s.substring(0, 1).toUpperCase(Locale.getDefault()) + s.substring(1);
    }

    public static String FormatAmount(int amount)
    {
        return String.format("₹ %s", amount);
    }
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat);
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat);

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
        }

        return outputDate;

    }
    public void createSnackBar(View view, String Message){
        Snackbar snackbar = Snackbar.make(view, Message, Snackbar.LENGTH_LONG);
        snackbar.show();
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
    //        final MaterialStyledDialog.Builder dialogHeader_6 = new MaterialStyledDialog.Builder(context)
//                .setStyle(Style.HEADER_WITH_TITLE)
//                .withDialogAnimation(true)
//                .setTitle("OOPS!")
//                .setDescription("Something went wrong. Please wait a moment and try again.")
//                .setHeaderColor(R.color.color_error)
//                .setPositiveText("Try Again")
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                    }
//                })
//                .setNegativeText("Later");
////
//        dialogHeader_6.show();
    //        MaterialStyledDialog.Builder dialogHeader_1 = new MaterialStyledDialog.Builder(context)
//                .setIcon(R.drawable.ic_server_error)
//                .withDialogAnimation(true)
//                .setTitle("Server Error!")
//                .setDescription("We couldn't connect to server. Check your internet connection and try again.")
//                .setHeaderColor(R.color.colorBlack)
//                .setPositiveText("Try Again")
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                    }
//                })
//                .setNegativeText("Later");
//        dialogHeader_1.show();

//        MaterialStyledDialog.Builder dialogHeader_1 = new MaterialStyledDialog.Builder(context)
//                .setIcon(R.drawable.ic_if_icon_no_internet)
//                .withDialogAnimation(true)
//                .setTitle("No Internet Connection!")
//                .setDescription("It looks like your internet connection is off. Please turn it on and try again.")
//                .setHeaderColor(R.color.color_error)
//                .setPositiveText("Try Again")
//                .withDivider(true)
//                .withDialogAnimation(true)
//                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                    @Override
//                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                    }
//                })
//                .setNegativeText("Later");
//        dialogHeader_1.show();
public static String getLocationAddress(double latitude, double longitude, Context context) {

    if (latitude != 0) {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        // Get the current location from the input parameter list
        // Create a list to contain the result address
        List<Address> addresses = null;
        try {
            /*
             * Return 1 address.
             */
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

        } catch (IOException e1) {
            e1.printStackTrace();
            return ("IO Exception trying to get address:" + e1);
        } catch (IllegalArgumentException e2) {
            // Error message to post in the log
            String errorString = "Illegal arguments "
                    + "passed to address service";
            e2.printStackTrace();
            return errorString;
        }
        // If the reverse geocode returned an address
        if (addresses != null && addresses.size() > 0) {
            // Get the first address
            Address address = addresses.get(0);
            /*
             * Format the first line of address (if available), city, and
             * country name.
             */
            String addressText = address.getAddressLine(0);
            // Return the text
            return addressText;
        } else {
            return "No address found by the service: Note to the developers, If no address is found by google itself, there is nothing you can do about it.";
        }
    } else {
        return "Location Not available";
    }

}


}
