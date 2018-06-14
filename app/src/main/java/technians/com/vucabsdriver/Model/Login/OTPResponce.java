package technians.com.vucabsdriver.Model.Login;

import com.google.gson.annotations.SerializedName;

public class OTPResponce {
    @SerializedName("msg")
    private String msg;
    @SerializedName("success")
    private String success;
    @SerializedName("token")
    private String token;
    @SerializedName("status")
    private int status;

    public OTPResponce(String success, String msg, String token, int status) {
        this.success = success;
        this.msg = msg;
        this.token = token;
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
