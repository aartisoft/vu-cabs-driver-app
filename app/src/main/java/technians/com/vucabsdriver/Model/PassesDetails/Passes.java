package technians.com.vucabsdriver.Model.PassesDetails;

import com.google.gson.annotations.SerializedName;

public class Passes {
    @SerializedName("id")
    private int id;
    @SerializedName("car_type")
    private String car_type;
    @SerializedName("amount")
    private String amount;
    @SerializedName("ride_qty")
    private String ride_qty;

    public Passes(int id, String car_type, String amount, String ride_qty) {
        this.id = id;
        this.car_type = car_type;
        this.amount = amount;
        this.ride_qty = ride_qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRide_qty() {
        return ride_qty;
    }

    public void setRide_qty(String ride_qty) {
        this.ride_qty = ride_qty;
    }
}
