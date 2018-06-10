package technians.com.vucabsdriver.model;

/**
 * Created by WINDOW 64 BIT on 12/9/2017.
 */

public class UserProfile {
    private int Driver_ID,Driver_Status;
    private String Name,Email,MobileNumber,Address,Car_Name,Car_Number,
            Car_Seat,Car_Type,CarURL,profileURL;

    public UserProfile(){

    }
    public UserProfile(int driver_ID, int driver_Status, String name, String email, String mobileNumber, String address, String car_Name, String car_Number, String car_Seat, String car_Type, String carURL, String profileURL) {
        Driver_ID = driver_ID;
        Driver_Status = driver_Status;
        Name = name;
        Email = email;
        MobileNumber = mobileNumber;
        Address = address;
        Car_Name = car_Name;
        Car_Number = car_Number;
        Car_Seat = car_Seat;
        Car_Type = car_Type;
        CarURL = carURL;
        this.profileURL = profileURL;
    }

    public int getDriver_ID() {
        return Driver_ID;
    }

    public void setDriver_ID(int driver_ID) {
        Driver_ID = driver_ID;
    }

    public int getDriver_Status() {
        return Driver_Status;
    }

    public void setDriver_Status(int driver_Status) {
        Driver_Status = driver_Status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCar_Name() {
        return Car_Name;
    }

    public void setCar_Name(String car_Name) {
        Car_Name = car_Name;
    }

    public String getCar_Number() {
        return Car_Number;
    }

    public void setCar_Number(String car_Number) {
        Car_Number = car_Number;
    }

    public String getCar_Seat() {
        return Car_Seat;
    }

    public void setCar_Seat(String car_Seat) {
        Car_Seat = car_Seat;
    }

    public String getCar_Type() {
        return Car_Type;
    }

    public void setCar_Type(String car_Type) {
        Car_Type = car_Type;
    }

    public String getCarURL() {
        return CarURL;
    }

    public void setCarURL(String carURL) {
        CarURL = carURL;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }
}
