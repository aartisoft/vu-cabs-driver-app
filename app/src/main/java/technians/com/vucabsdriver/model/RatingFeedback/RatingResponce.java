package technians.com.vucabsdriver.model.RatingFeedback;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import technians.com.vucabsdriver.model.Profile.Profile;

public class RatingResponce {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private ArrayList<Rating> ratingArrayList;

    public RatingResponce(Boolean success, int status, ArrayList<Rating> ratingArrayList) {
        this.success = success;
        this.status = status;
        this.ratingArrayList = ratingArrayList;
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

    public ArrayList<Rating> getRatingArrayList() {
        return ratingArrayList;
    }

    public void setRatingArrayList(ArrayList<Rating> ratingArrayList) {
        this.ratingArrayList = ratingArrayList;
    }
}
