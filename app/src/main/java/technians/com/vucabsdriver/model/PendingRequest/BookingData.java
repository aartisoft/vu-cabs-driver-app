package technians.com.vucabsdriver.model.PendingRequest;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BookingData extends RealmObject{
    @SerializedName("customer_name")
    private String customer_name;
    @SerializedName("customer_mobile")
    private String customer_mobile;
    @SerializedName("customer_email")
    private String customer_email;
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @SerializedName("order_id")
    private String order_id;
    @SerializedName("pick_loc")
    private String pickup_location;
    @SerializedName("pick_lat")
    private double pickup_latitude;
    @SerializedName("pick_lng")
    private double pickup_longitude;
    @SerializedName("drop_loc")
    private String drop_location;
    @SerializedName("drop_lat")
    private double drop_latitude;
    @SerializedName("drop_lng")
    private double drop_longitude;
    @SerializedName("distance")
    private String distance;
    @SerializedName("time_duration")
    private String time_duration;
    @SerializedName("payment_type")
    private String payment_type;
    @SerializedName("gst_amount")
    private int gst_amount;
    @SerializedName("ride_amount")
    private int ride_amount;
    @SerializedName("total_amount")
    private int total_amount;
    @SerializedName("toll")
    private int toll;
    @SerializedName("status")
    private int status;
    @SerializedName("date")
    private String date;

    public BookingData(){

    }
    public BookingData(String customer_name, String customer_mobile, String customer_email, int id, String order_id, String pickup_location, double pickup_latitude, double pickup_longitude, String drop_location, double drop_latitude, double drop_longitude, String distance, String time_duration, String payment_type, int gst_amount, int ride_amount, int total_amount, int toll, int status, String date) {
        this.customer_name = customer_name;
        this.customer_mobile = customer_mobile;
        this.customer_email = customer_email;
        this.id = id;
        this.order_id = order_id;
        this.pickup_location = pickup_location;
        this.pickup_latitude = pickup_latitude;
        this.pickup_longitude = pickup_longitude;
        this.drop_location = drop_location;
        this.drop_latitude = drop_latitude;
        this.drop_longitude = drop_longitude;
        this.distance = distance;
        this.time_duration = time_duration;
        this.payment_type = payment_type;
        this.gst_amount = gst_amount;
        this.ride_amount = ride_amount;
        this.total_amount = total_amount;
        this.toll = toll;
        this.status = status;
        this.date = date;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public double getPickup_latitude() {
        return pickup_latitude;
    }

    public void setPickup_latitude(double pickup_latitude) {
        this.pickup_latitude = pickup_latitude;
    }

    public double getPickup_longitude() {
        return pickup_longitude;
    }

    public void setPickup_longitude(double pickup_longitude) {
        this.pickup_longitude = pickup_longitude;
    }

    public String getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(String drop_location) {
        this.drop_location = drop_location;
    }

    public double getDrop_latitude() {
        return drop_latitude;
    }

    public void setDrop_latitude(double drop_latitude) {
        this.drop_latitude = drop_latitude;
    }

    public double getDrop_longitude() {
        return drop_longitude;
    }

    public void setDrop_longitude(double drop_longitude) {
        this.drop_longitude = drop_longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime_duration() {
        return time_duration;
    }

    public void setTime_duration(String time_duration) {
        this.time_duration = time_duration;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public int getGst_amount() {
        return gst_amount;
    }

    public void setGst_amount(int gst_amount) {
        this.gst_amount = gst_amount;
    }

    public int getRide_amount() {
        return ride_amount;
    }

    public void setRide_amount(int ride_amount) {
        this.ride_amount = ride_amount;
    }

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public int getToll() {
        return toll;
    }

    public void setToll(int toll) {
        this.toll = toll;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
