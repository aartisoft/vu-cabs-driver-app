package technians.com.vucabsdriver.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "https://www.vucabs.com/api/driver/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        OkHttpClient.Builder  okHttpClient = new OkHttpClient.Builder().
                connectTimeout(100, TimeUnit.SECONDS).
                writeTimeout(10, TimeUnit.SECONDS).
                readTimeout(30, TimeUnit.SECONDS);
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient.build())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit getPaytmClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.vucabs.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
