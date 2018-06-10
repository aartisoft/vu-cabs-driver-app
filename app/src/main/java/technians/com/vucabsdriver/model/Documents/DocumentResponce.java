package technians.com.vucabsdriver.model.Documents;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DocumentResponce {
    @SerializedName("msg")
    private String msg;
    @SerializedName("success")
    private String success;
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private ArrayList<DocumentsList> documentsLists;

    public DocumentResponce(String msg, String success, int status, ArrayList<DocumentsList> documentsLists) {
        this.msg = msg;
        this.success = success;
        this.status = status;
        this.documentsLists = documentsLists;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<DocumentsList> getDocumentsLists() {
        return documentsLists;
    }

    public void setDocumentsLists(ArrayList<DocumentsList> documentsLists) {
        this.documentsLists = documentsLists;
    }
}
