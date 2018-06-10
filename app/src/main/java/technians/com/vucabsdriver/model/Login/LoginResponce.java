package technians.com.vucabsdriver.model.Login;

import com.google.gson.annotations.SerializedName;

public class LoginResponce {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private int status;

    public LoginResponce(Boolean success, String msg, int status) {
        this.success = success;
        this.msg = msg;
        this.status = status;
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
}
