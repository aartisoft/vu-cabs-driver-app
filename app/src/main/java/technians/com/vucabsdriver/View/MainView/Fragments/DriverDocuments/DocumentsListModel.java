package technians.com.vucabsdriver.View.MainView.Fragments.DriverDocuments;

/**
 * Created by vineet on 10/27/2017.
 */

public class DocumentsListModel {
    private String docName, docStatus,docUrl;

    public DocumentsListModel(String docName, String docStatus, String docUrl) {
        this.docName = docName;
        this.docStatus = docStatus;
        this.docUrl = docUrl;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
}
