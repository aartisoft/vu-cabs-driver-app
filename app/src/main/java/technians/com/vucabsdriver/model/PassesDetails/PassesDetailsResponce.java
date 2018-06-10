package technians.com.vucabsdriver.model.PassesDetails;

import com.google.gson.annotations.SerializedName;

public class PassesDetailsResponce {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private PassesData passesData;

    public PassesDetailsResponce(Boolean success, String msg, int status, PassesData passesData) {
        this.success = success;
        this.msg = msg;
        this.status = status;
        this.passesData = passesData;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PassesData getPassesData() {
        return passesData;
    }

    public void setPassesData(PassesData passesData) {
        this.passesData = passesData;
    }
}
