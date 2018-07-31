package technians.com.vucabsdriver.Model.TripEnd;

import com.google.gson.annotations.SerializedName;

public class DataResponce {
    @SerializedName("invoice")
    private TripEndData invoice;

    public DataResponce(TripEndData invoice) {
        this.invoice = invoice;
    }

    public TripEndData getInvoice() {
        return invoice;
    }

    public void setInvoice(TripEndData invoice) {
        this.invoice = invoice;
    }
}
