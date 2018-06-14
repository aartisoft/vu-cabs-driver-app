package technians.com.vucabsdriver.Model.PassesRecharge;

import com.google.gson.annotations.SerializedName;

public class RechargeDetails {
    @SerializedName("driver_id")
    private String driver_id;
    @SerializedName("name")
    private String name;
    @SerializedName("amount")
    private String amount;
    @SerializedName("ride_qty")
    private String ride_qty;

    public RechargeDetails(String driver_id, String name, String amount, String ride_qty) {
        this.driver_id = driver_id;
        this.name = name;
        this.amount = amount;
        this.ride_qty = ride_qty;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
