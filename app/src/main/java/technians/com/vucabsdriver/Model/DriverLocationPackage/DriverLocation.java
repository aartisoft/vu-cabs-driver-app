package technians.com.vucabsdriver.Model.DriverLocationPackage;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vineet on 12/12/2017.
 */

public class DriverLocation extends RealmObject{
    @PrimaryKey
    private int id;
    private double latitude;
    private double longitude;

    public DriverLocation(){

    }
    public DriverLocation(int id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
