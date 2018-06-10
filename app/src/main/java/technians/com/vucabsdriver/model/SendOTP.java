package technians.com.vucabsdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vineet on 5/8/2018.
 */

public class  SendOTP {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("status")
    private int status;

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
}
