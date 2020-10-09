package com.example.iotcommunication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<StringResponse> acclogin(@Field("email") String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("signup.php")
    Call<StringResponse> accsignup(@Field("name") String name,@Field("email") String email,@Field("password") String password);

    @POST("downloadmessage.php")
    Call<List<String>> downloadmessage();

    @POST("downloaddetails.php")
    Call<Patient> downloaddetails();

}
