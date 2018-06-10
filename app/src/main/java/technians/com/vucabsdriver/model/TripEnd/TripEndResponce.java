package technians.com.vucabsdriver.model.TripEnd;

import com.google.gson.annotations.SerializedName;

public class TripEndResponce {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private TripEndData tripEndData;

    public TripEndResponce(Boolean success, int status, TripEndData tripEndData) {
        this.success = success;
        this.status = status;
        this.tripEndData = tripEndData;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TripEndData getTripEndData() {
        return tripEndData;
    }

    public void setTripEndData(TripEndData tripEndData) {
        this.tripEndData = tripEndData;
    }
}
