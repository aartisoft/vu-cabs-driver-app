package technians.com.vucabsdriver.Model.PassesDetails;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PassesData {
    @SerializedName("recharge_count")
    private String recharge_count;
    @SerializedName("car_fare")
    private ArrayList<Passes> passesArrayList;

    public PassesData(String recharge_count, ArrayList<Passes> passesArrayList) {
        this.recharge_count = recharge_count;
        this.passesArrayList = passesArrayList;
    }

    public String getRecharge_count() {
        return recharge_count;
    }

    public void setRecharge_count(String recharge_count) {
        this.recharge_count = recharge_count;
    }

    public ArrayList<Passes> getPassesArrayList() {
        return passesArrayList;
    }

    public void setPassesArrayList(ArrayList<Passes> passesArrayList) {
        this.passesArrayList = passesArrayList;
    }
}
