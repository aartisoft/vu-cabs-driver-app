package technians.com.vucabsdriver.Model.PendingRequest;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PendingRequestData {
    @SerializedName("total_earn")
    private int total_earn;
    @SerializedName("total_booking")
    private int total_booking;
    @SerializedName("booking_data")
    private ArrayList<BookingData> bookingData;

    public PendingRequestData(int total_earn, int total_booking, ArrayList<BookingData> bookingData) {
        this.total_earn = total_earn;
        this.total_booking = total_booking;
        this.bookingData = bookingData;
    }

    public int getTotal_earn() {
        return total_earn;
    }

    public void setTotal_earn(int total_earn) {
        this.total_earn = total_earn;
    }

    public int getTotal_booking() {
        return total_booking;
    }

    public void setTotal_booking(int total_booking) {
        this.total_booking = total_booking;
    }

    public ArrayList<BookingData> getBookingData() {
        return bookingData;
    }

    public void setBookingData(ArrayList<BookingData> bookingData) {
        this.bookingData = bookingData;
    }
}
