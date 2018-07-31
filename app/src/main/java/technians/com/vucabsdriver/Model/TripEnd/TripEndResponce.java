package technians.com.vucabsdriver.Model.TripEnd;

import com.google.gson.annotations.SerializedName;

public class TripEndResponce {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private DataResponce responce;
//    private TripEndData tripEndData;

//    @SerializedName("data")
//    private TripEndData tripEndData;

    public TripEndResponce(Boolean success, int status, DataResponce responce) {
        this.success = success;
        this.status = status;
        this.responce = responce;
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

    public DataResponce getResponce() {
        return responce;
    }

    public void setResponce(DataResponce responce) {
        this.responce = responce;
    }
}
