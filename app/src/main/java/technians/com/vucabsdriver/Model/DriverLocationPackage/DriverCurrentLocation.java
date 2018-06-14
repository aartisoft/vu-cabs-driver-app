package technians.com.vucabsdriver.Model.DriverLocationPackage;

import java.io.Serializable;

/**
 * Created by vineet on 12/12/2017.
 */

public class DriverCurrentLocation implements Serializable{
    private String address,updated_at;
    private int car_type,driver_id,status;
    private double latitude,longitude;

    public DriverCurrentLocation(String address, String updated_at, int car_type, int driver_id, int status, double latitude, double longitude) {
        this.address = address;
        this.updated_at = updated_at;
        this.car_type = car_type;
        this.driver_id = driver_id;
        this.status = status;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCar_type() {
        return car_type;
    }

    public void setCar_type(int car_type) {
        this.car_type = car_type;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
