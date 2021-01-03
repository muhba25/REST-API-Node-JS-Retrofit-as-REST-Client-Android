package com.andyadr.apps.testingapi.API;

import com.google.gson.annotations.SerializedName;

public class responsePostPutDelMahasiswa {
    @SerializedName("meesage")
    String  message;

    public String getMessage() {
        return message;
    }
    public void setMessage(String  user) {
        this.message = user;
    }
}
