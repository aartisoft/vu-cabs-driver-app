package technians.com.vucabsdriver.Model.RatingFeedback;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("rating")
    private int rating;
    @SerializedName("comment")
    private String comment;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("cus_name")
    private String cus_name;

    public Rating(int rating, String comment, String updated_at, String cus_name) {
        this.rating = rating;
        this.comment = comment;
        this.updated_at = updated_at;
        this.cus_name = cus_name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }
}
