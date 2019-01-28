package com.codecaique3296.delivrenoapp.models.response;

import com.codecaique3296.delivrenoapp.models.object.Phone;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhoneJson {
    @SerializedName("error")
    boolean error;
    @SerializedName("ack")
    String ack;
    @SerializedName("phones")
    List<Phone> phones;

    public PhoneJson(boolean error, String ack, List<Phone> phones) {
        this.error = error;
        this.ack = ack;
        this.phones = phones;
    }


    public boolean isError() {
        return error;
    }

    public String getAck() {
        return ack;
    }

    public List<Phone> getPhones() {
        return phones;
    }
}
