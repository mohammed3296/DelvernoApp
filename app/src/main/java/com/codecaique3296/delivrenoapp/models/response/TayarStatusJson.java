package com.codecaique3296.delivrenoapp.models.response;

import com.google.gson.annotations.SerializedName;

public class TayarStatusJson {
    @SerializedName("status")
    boolean status ;

    public TayarStatusJson(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }
}
