package technians.com.vucabsdriver.model.TripEnd;

import com.google.gson.annotations.SerializedName;

public class TripEndData {
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_email")
    private String customer_email;
    @SerializedName("booking_id")
    private int booking_id;
    @SerializedName("pick_loc")
    private String pick_loc;
    @SerializedName("drop_loc")
    private String drop_loc;
    @SerializedName("payment_mode")
    private String payment_mode;
    @SerializedName("gst_amount")
    private String gst_amount;
    @SerializedName("total_amount")
    private String total_amount;
    @SerializedName("ride_amount")
    private String ride_amount;
    @SerializedName("toll")
    private int toll;

    public TripEndData(String customer_name, String customer_email, int booking_id, String pick_loc, String drop_loc, String payment_mode, String gst_amount, String total_amount, String ride_amount, int toll) {
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.booking_id = booking_id;
        this.pick_loc = pick_loc;
        this.drop_loc = drop_loc;
        this.payment_mode = payment_mode;
        this.gst_amount = gst_amount;
        this.total_amount = total_amount;
        this.ride_amount = ride_amount;
        this.toll = toll;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getPick_loc() {
        return pick_loc;
    }

    public void setPick_loc(String pick_loc) {
        this.pick_loc = pick_loc;
    }

    public String getDrop_loc() {
        return drop_loc;
    }

    public void setDrop_loc(String drop_loc) {
        this.drop_loc = drop_loc;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getGst_amount() {
        return gst_amount;
    }

    public void setGst_amount(String gst_amount) {
        this.gst_amount = gst_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getRide_amount() {
        return ride_amount;
    }

    public void setRide_amount(String ride_amount) {
        this.ride_amount = ride_amount;
    }

    public int getToll() {
        return toll;
    }

    public void setToll(int toll) {
        this.toll = toll;
    }
}
