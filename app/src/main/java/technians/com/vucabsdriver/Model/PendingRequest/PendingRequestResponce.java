package technians.com.vucabsdriver.Model.PendingRequest;

import com.google.gson.annotations.SerializedName;

import technians.com.vucabsdriver.Model.PassesRecharge.RechargeDetails;

public class PendingRequestResponce {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private PendingRequestData pendingRequestData;


    public PendingRequestResponce(Boolean success, String msg, int status, PendingRequestData pendingRequestData) {
        this.success = success;
        this.msg = msg;
        this.status = status;
        this.pendingRequestData = pendingRequestData;
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

    public PendingRequestData getPendingRequestData() {
        return pendingRequestData;
    }

    public void setPendingRequestData(PendingRequestData pendingRequestData) {
        this.pendingRequestData = pendingRequestData;
    }
}
