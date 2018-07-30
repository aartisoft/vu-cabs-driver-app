package technians.com.vucabsdriver.Model.Profile;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Profile extends RealmObject implements Serializable{
    @PrimaryKey
    @SerializedName("id")
    private int Driver_ID;
    @SerializedName("status")
    private int Driver_Status;
    @SerializedName("name")
    private String Name;
    @SerializedName("email")
    private String Email;
    @SerializedName("mobile")
    private String MobileNumber;
    @SerializedName("address")
    private String Address;
    @SerializedName("car_name")
    private String Car_Name;
    @SerializedName("car_no")
    private String Car_Number;
    @SerializedName("car_seat")
    private String Car_Seat;
    @SerializedName("car_type")
    private int Car_Type;
    @SerializedName("car_type_name")
    private String Car_Type_Name;
    @SerializedName("car_img")
    private String CarURL;
    @SerializedName("profile_img")
    private String profileURL;
    @SerializedName("aadhar_no")
    private String aadhar_no;

    public Profile(){

    }

    public Profile(int driver_ID, int driver_Status, String name, String email, String mobileNumber, String address, String car_Name, String car_Number, String car_Seat, int car_Type, String car_Type_Name, String carURL, String profileURL, String aadhar_no) {
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
        Car_Type_Name = car_Type_Name;
        CarURL = carURL;
        this.profileURL = profileURL;
        this.aadhar_no = aadhar_no;
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

    public int getCar_Type() {
        return Car_Type;
    }

    public void setCar_Type(int car_Type) {
        Car_Type = car_Type;
    }

    public String getCar_Type_Name() {
        return Car_Type_Name;
    }

    public void setCar_Type_Name(String car_Type_Name) {
        Car_Type_Name = car_Type_Name;
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

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }
}
