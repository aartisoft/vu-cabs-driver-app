package technians.com.vucabsdriver.CustomInfoWindow;

public class InfoWindowData {
    private String myLocation,Address,last_updated,status;

    public InfoWindowData(){

    }
    public InfoWindowData(String myLocation, String address, String last_updated, String status) {
        this.myLocation = myLocation;
        Address = address;
        this.last_updated = last_updated;
        this.status = status;
    }

    public String getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(String myLocation) {
        this.myLocation = myLocation;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
