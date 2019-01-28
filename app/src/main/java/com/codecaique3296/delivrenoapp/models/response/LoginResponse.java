package com.codecaique3296.delivrenoapp.models.response;

import com.codecaique3296.delivrenoapp.models.object.Tayar;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {
    @SerializedName("error")
    boolean error;
    @SerializedName("ack")
    String ack;
    @SerializedName("tayar")
    Tayar tayar ;

    public LoginResponse(boolean error, String ack, Tayar tayar) {
        this.error = error;
        this.ack = ack;
        this.tayar = tayar;
    }

    public boolean isError() {
        return error;
    }

    public String getAck() {
        return ack;
    }

    public Tayar getTayar() {
        return tayar;
    }
}

