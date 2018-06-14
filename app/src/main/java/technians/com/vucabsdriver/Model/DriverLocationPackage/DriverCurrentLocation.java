package technians.com.vucabsdriver.Model.DriverLocationPackage;

import java.io.Serializable;

import technians.com.vucabsdriver.Model.Profile.Profile;

/**
 * Created by vineet on 12/12/2017.
 */

public class DriverCurrentLocation implements Serializable{
    private String address,updated_at;
    private Profile profile;

    public DriverCurrentLocation(String address, String updated_at, Profile profile) {
        this.address = address;
        this.updated_at = updated_at;
        this.profile = profile;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
