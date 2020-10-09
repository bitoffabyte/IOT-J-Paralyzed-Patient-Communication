package com.example.iotcommunication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String base_url="http://192.168.43.119:8080/Iot_comm/";//server URL
    private static Retrofit retrofit=null;
    public static Retrofit getClient(){
        if(retrofit==null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
