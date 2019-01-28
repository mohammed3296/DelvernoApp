package com.codecaique3296.delivrenoapp.models.response;

import com.google.gson.annotations.SerializedName;

public class PublicResponse {
    @SerializedName("error")
    boolean error;
    @SerializedName("ack")
    String ack;

    public PublicResponse(boolean error, String ack) {
        this.error = error;
        this.ack = ack;
    }

    public boolean isError() {
        return error;
    }

    public String getAck() {
        return ack;
    }
}
