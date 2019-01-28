package com.codecaique3296.delivrenoapp.models.response;

import com.codecaique3296.delivrenoapp.models.object.Resturant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResturantsJson {
    @SerializedName("error")
    boolean error;
    @SerializedName("ack")
    String ack;
    @SerializedName("resturants")
    List<Resturant> resturants ;

    public ResturantsJson(boolean error, String ack, List<Resturant> resturants) {
        this.error = error;
        this.ack = ack;
        this.resturants = resturants;
    }

    public boolean isError() {
        return error;
    }

    public String getAck() {
        return ack;
    }

    public List<Resturant> getResturants() {
        return resturants;
    }
}
