package technians.com.vucabsdriver.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by vineet on 10/18/2017.
 */

//        android:fontFamily="sans-serif"           // roboto regular
//        android:fontFamily="sans-serif-light"     // roboto light
//        android:fontFamily="sans-serif-condensed" // roboto condensed
//        android:fontFamily="sans-serif-thin"      // roboto thin (android 4.2)
//        android:fontFamily="sans-serif-medium"    // roboto medium (android 5.0)

public class Constants {

    public static final String PayUMoneyHash = "https://www.vucabs.com/api/moneyhash-android";
    public static final int PayUMoneyRequestCode = 2;

    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage("Please wait");
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        return m_Dialog;
    }

    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        return m_Dialog;
    }

//    public static void ShowError(String error, Context context) {
//        switch (error) {
//            case "Check internet connection": {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("No Internet Connection")
//                        .setMessage("It looks like your internet connection is off. Please turn it " +
//                                "on and try again.")
//                        .setCancelable(false)
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//
//                            }
//                        }).setIcon(android.R.drawable.ic_dialog_alert).show();
//                break;
//            }
//            case "Request timeout": {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Request timed out")
//                        .setMessage("We couldn't connect to server. Check your internet connection and try again.")
//                        .setCancelable(true)
//                        .show();
//                break;
//            }
//            case "Some error occurred":{
//                Toast toast = Toast.makeText(context,"Some error occurred" , Toast.LENGTH_SHORT);
//                toast.show();
//                break;
//            }
//            default:{
//                Toast toast = Toast.makeText(context,"Some error occurred" , Toast.LENGTH_SHORT);
//                toast.show();
//                break;
//            }
//        }
//
//    }


    public static String FormatAmount(float amount) {
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
                return "No address found.";
            }
        } else {
            return "Location Not available";
        }

    }


}
