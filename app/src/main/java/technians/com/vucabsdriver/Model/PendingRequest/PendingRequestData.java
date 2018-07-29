package technians.com.vucabsdriver.Model.PendingRequest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PendingRequestData {
    @SerializedName("total_earn")
    private float total_earn;
    @SerializedName("total_booking")
    private float total_booking;
    @SerializedName("booking_data")
    private ArrayList<BookingData> bookingData;

    public PendingRequestData(float total_earn, float total_booking, ArrayList<BookingData> bookingData) {
        this.total_earn = total_earn;
        this.total_booking = total_booking;
        this.bookingData = bookingData;
    }

    public float getTotal_earn() {
        return total_earn;
    }

    public void setTotal_earn(float total_earn) {
        this.total_earn = total_earn;
    }

    public float getTotal_booking() {
        return total_booking;
    }

    public void setTotal_booking(float total_booking) {
        this.total_booking = total_booking;
    }

    public ArrayList<BookingData> getBookingData() {
        return bookingData;
    }

    public void setBookingData(ArrayList<BookingData> bookingData) {
        this.bookingData = bookingData;
    }
}
