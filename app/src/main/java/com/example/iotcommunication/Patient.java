package com.example.iotcommunication;

import com.google.gson.annotations.SerializedName;

public class Patient {
    @SerializedName("name")
    public String name;

    @SerializedName("contact")
    public String contact;

    @SerializedName("email")
    public  String email;

    @SerializedName("id")
    public String id;

    @SerializedName("address")
    public String address;
}
