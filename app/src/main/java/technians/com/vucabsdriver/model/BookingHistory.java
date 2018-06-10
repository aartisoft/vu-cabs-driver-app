package technians.com.vucabsdriver.model;

/**
 * Created by WINDOW 64 BIT on 11/19/2017.
 */

public class BookingHistory {
    private String CustomerName,CustomerMobile,CustomerEmail,PickLocation
            ,Order_ID,DropLocation,PaymentMode,Trip_Distance,Date,TimeDuration,BookingStatus;
    private int TollAmount,Gst_Amount,Ride_Amount,Total_Amount,id,booking_status,total_earning,total_bookingcount;
    private double PickLat,PickLng,DropLat,DropLng;

    public BookingHistory(String customerName, String customerMobile, String customerEmail, String pickLocation, String order_ID, String dropLocation, String paymentMode, String trip_Distance, String date, String timeDuration, String bookingStatus, int tollAmount, int gst_Amount, int ride_Amount, int total_Amount, int id, int booking_status, int total_earning, int total_bookingcount, double pickLat, double pickLng, double dropLat, double dropLng) {
        CustomerName = customerName;
        CustomerMobile = customerMobile;
        CustomerEmail = customerEmail;
        PickLocation = pickLocation;
        Order_ID = order_ID;
        DropLocation = dropLocation;
        PaymentMode = paymentMode;
        Trip_Distance = trip_Distance;
        Date = date;
        TimeDuration = timeDuration;
        BookingStatus = bookingStatus;
        TollAmount = tollAmount;
        Gst_Amount = gst_Amount;
        Ride_Amount = ride_Amount;
        Total_Amount = total_Amount;
        this.id = id;
        this.booking_status = booking_status;
        this.total_earning = total_earning;
        this.total_bookingcount = total_bookingcount;
        PickLat = pickLat;
        PickLng = pickLng;
        DropLat = dropLat;
        DropLng = dropLng;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerMobile() {
        return CustomerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        CustomerMobile = customerMobile;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public String getPickLocation() {
        return PickLocation;
    }

    public void setPickLocation(String pickLocation) {
        PickLocation = pickLocation;
    }

    public String getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(String order_ID) {
        Order_ID = order_ID;
    }

    public String getDropLocation() {
        return DropLocation;
    }

    public void setDropLocation(String dropLocation) {
        DropLocation = dropLocation;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getTrip_Distance() {
        return Trip_Distance;
    }

    public void setTrip_Distance(String trip_Distance) {
        Trip_Distance = trip_Distance;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTimeDuration() {
        return TimeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        TimeDuration = timeDuration;
    }

    public String getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        BookingStatus = bookingStatus;
    }

    public int getTollAmount() {
        return TollAmount;
    }

    public void setTollAmount(int tollAmount) {
        TollAmount = tollAmount;
    }

    public int getGst_Amount() {
        return Gst_Amount;
    }

    public void setGst_Amount(int gst_Amount) {
        Gst_Amount = gst_Amount;
    }

    public int getRide_Amount() {
        return Ride_Amount;
    }

    public void setRide_Amount(int ride_Amount) {
        Ride_Amount = ride_Amount;
    }

    public int getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(int total_Amount) {
        Total_Amount = total_Amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(int booking_status) {
        this.booking_status = booking_status;
    }

    public int getTotal_earning() {
        return total_earning;
    }

    public void setTotal_earning(int total_earning) {
        this.total_earning = total_earning;
    }

    public int getTotal_bookingcount() {
        return total_bookingcount;
    }

    public void setTotal_bookingcount(int total_bookingcount) {
        this.total_bookingcount = total_bookingcount;
    }

    public double getPickLat() {
        return PickLat;
    }

    public void setPickLat(double pickLat) {
        PickLat = pickLat;
    }

    public double getPickLng() {
        return PickLng;
    }

    public void setPickLng(double pickLng) {
        PickLng = pickLng;
    }

    public double getDropLat() {
        return DropLat;
    }

    public void setDropLat(double dropLat) {
        DropLat = dropLat;
    }

    public double getDropLng() {
        return DropLng;
    }

    public void setDropLng(double dropLng) {
        DropLng = dropLng;
    }
}
