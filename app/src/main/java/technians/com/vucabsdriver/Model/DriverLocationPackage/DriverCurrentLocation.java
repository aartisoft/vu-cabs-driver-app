package technians.com.vucabsdriver.Model.DriverLocationPackage;

import java.io.Serializable;

/**
 * Created by vineet on 12/12/2017.
 */

public class DriverCurrentLocation implements Serializable {
    private String address, updated_at;
    private int car_type, driver_id, status;
    private double lat, lng;
    private String car_seat, driver_name, driver_mobile, driver_profileurl, car_name, car_number, car_picurl;
    private int drivingStatus;

    public DriverCurrentLocation() {

    }

    public DriverCurrentLocation(String address, String updated_at, int car_type, int driver_id, int status, double lat, double lng, String car_seat, String driver_name, String driver_mobile, String driver_profileurl, String car_name, String car_number, String car_picurl, int drivingStatus) {
        this.address = address;
        this.updated_at = updated_at;
        this.car_type = car_type;
        this.driver_id = driver_id;
        this.status = status;
        this.lat = lat;
        this.lng = lng;
        this.car_seat = car_seat;
        this.driver_name = driver_name;
        this.driver_mobile = driver_mobile;
        this.driver_profileurl = driver_profileurl;
        this.car_name = car_name;
        this.car_number = car_number;
        this.car_picurl = car_picurl;
        this.drivingStatus = drivingStatus;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCar_seat() {
        return car_seat;
    }

    public void setCar_seat(String car_seat) {
        this.car_seat = car_seat;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_mobile() {
        return driver_mobile;
    }

    public void setDriver_mobile(String driver_mobile) {
        this.driver_mobile = driver_mobile;
    }

    public String getDriver_profileurl() {
        return driver_profileurl;
    }

    public void setDriver_profileurl(String driver_profileurl) {
        this.driver_profileurl = driver_profileurl;
    }

    public String getCar_name() {
        return car_name;
    }

    public void setCar_name(String car_name) {
        this.car_name = car_name;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getCar_picurl() {
        return car_picurl;
    }

    public void setCar_picurl(String car_picurl) {
        this.car_picurl = car_picurl;
    }

    public int getDrivingStatus() {
        return drivingStatus;
    }

    public void setDrivingStatus(int drivingStatus) {
        this.drivingStatus = drivingStatus;
    }
}
