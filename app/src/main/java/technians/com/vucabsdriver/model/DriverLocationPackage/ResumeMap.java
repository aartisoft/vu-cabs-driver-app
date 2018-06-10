package technians.com.vucabsdriver.model.DriverLocationPackage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ResumeMap extends RealmObject{
    @PrimaryKey
    private int id;
    private String Address,lastupdated;
    private Boolean DriverStatus;
    private double latitude,longitude;

    public ResumeMap(){

    }
    public ResumeMap(int id, String address, String lastupdated, Boolean driverStatus, double latitude, double longitude) {
        this.id = id;
        Address = address;
        this.lastupdated = lastupdated;
        DriverStatus = driverStatus;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Boolean getDriverStatus() {
        return DriverStatus;
    }

    public void setDriverStatus(Boolean driverStatus) {
        DriverStatus = driverStatus;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
