package technians.com.vucabsdriver.Model.Profile;

import com.google.gson.annotations.SerializedName;

public class ProfileResponce {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private Profile profile;

    public ProfileResponce(Boolean success, int status, Profile profile) {
        this.success = success;
        this.status = status;
        this.profile = profile;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
