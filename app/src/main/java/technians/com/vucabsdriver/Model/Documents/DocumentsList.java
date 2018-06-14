package technians.com.vucabsdriver.Model.Documents;

import com.google.gson.annotations.SerializedName;

public class DocumentsList {
    @SerializedName("doc_name")
    private String doc_name;
    @SerializedName("img_path")
    private String img_path;
    @SerializedName("image_upload")
    private String image_upload;

    public DocumentsList(String doc_name, String img_path, String image_upload) {
        this.doc_name = doc_name;
        this.img_path = img_path;
        this.image_upload = image_upload;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getImage_upload() {
        return image_upload;
    }

    public void setImage_upload(String image_upload) {
        this.image_upload = image_upload;
    }
}
