package technians.com.vucabsdriver.rest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import technians.com.vucabsdriver.model.Documents.DocumentResponce;
import technians.com.vucabsdriver.model.Login.LoginResponce;
import technians.com.vucabsdriver.model.Login.OTPResponce;
import technians.com.vucabsdriver.model.PassesDetails.PassesDetailsResponce;
import technians.com.vucabsdriver.model.PassesRecharge.PassesRechargeResponce;
import technians.com.vucabsdriver.model.PendingRequest.PendingRequestResponce;
import technians.com.vucabsdriver.model.Profile.ProfileResponce;
import technians.com.vucabsdriver.model.RatingFeedback.RatingResponce;
import technians.com.vucabsdriver.model.TripEnd.TripEndResponce;


public interface ApiInterface {
//    @Field parameters can only be used with @FormUrlEncoded.

    @FormUrlEncoded
    @POST("driver-login")
    Call<LoginResponce> sendOTP(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("otp")
    Call<OTPResponce> checkOTP(@Field("mobile") String mobile, @Field("otp") String otp);

    @POST("get-details")
    Call<ProfileResponce> getProfile(@Query("token") String token);

    @GET("document-list")
    Call<DocumentResponce> getDocumentsList(@Query("token") String token);

    @GET("car-fare-price-driver")
    Call<PassesDetailsResponce> getPassesDetails(@Query("token") String token);

    @FormUrlEncoded
    @POST("recharge")
    Call<PassesRechargeResponce> getRechargeDetails(@Query("token") String token, @Field("driver_id") String driver_id,
                                                    @Field("name") String name, @Field("amount") String amount,
                                                    @Field("ride_qty") String ride_qty);

    @FormUrlEncoded
    @POST("rating-driver")
    Call<RatingResponce> getRatingfeedback(@Query("token") String token, @Field("driver_id") int driverid);

    @GET("driver-booking")
    Call<PendingRequestResponce> getPendingRequest(@Query("token") String token);

    @FormUrlEncoded
    @POST("booking-verify")
    Call<OTPResponce> bookingverify(@Query("token") String token,@Field("mobile") String mobile, @Field("otp") String otp);

    @FormUrlEncoded
    @POST("toll")
    Call<LoginResponce> addToll(@Query("token") String token, @Field("driver_id") String driver_id, @Field("booking_id") String booking_id,
                                @Field("toll_name") String toll_name, @Field("toll_address") String toll_address, @Field("toll_lat") String toll_lat, @Field("toll_lng") String toll_lng
                              , @Field("toll_price") String toll_price);

    @FormUrlEncoded
    @POST("driver-ride")
    Call<TripEndResponce> tripEnd(@Query("token") String token, @Field("driver_id") String driver_id,
                                  @Field("order_id") String order_id
    ,@Field("address") String address,@Field("lat") double lat,@Field("lng") double lng,@Field("status") int status);
}
