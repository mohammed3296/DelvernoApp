package com.codecaique3296.delivrenoapp.models.response;

import com.codecaique3296.delivrenoapp.models.object.Request;
import com.codecaique3296.delivrenoapp.models.object.Resturant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestsJson {
    @SerializedName("error")
    boolean error;
    @SerializedName("ack")
    String ack;
    @SerializedName("requests")
    List<Request> requests ;

    public RequestsJson(boolean error, String ack, List<Request> requests) {
        this.error = error;
        this.ack = ack;
        this.requests = requests;
    }

    public boolean isError() {
        return error;
    }

    public String getAck() {
        return ack;
    }

    public List<Request> getRequests() {
        return requests;
    }

}
