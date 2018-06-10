package technians.com.vucabsdriver.model.PassesRecharge;

import com.google.gson.annotations.SerializedName;

public class PassesRechargeResponce {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private RechargeDetails rechargeDetails;

    public PassesRechargeResponce(Boolean success, String msg, int status, RechargeDetails rechargeDetails) {
        this.success = success;
        this.msg = msg;
        this.status = status;
        this.rechargeDetails = rechargeDetails;
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

    public RechargeDetails getRechargeDetails() {
        return rechargeDetails;
    }

    public void setRechargeDetails(RechargeDetails rechargeDetails) {
        this.rechargeDetails = rechargeDetails;
    }
}
