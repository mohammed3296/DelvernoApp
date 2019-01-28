package com.codecaique3296.delivrenoapp.models.response;

import com.google.gson.annotations.SerializedName;

public class ImageUpdateJson {
    @SerializedName("error")
    boolean error;
    @SerializedName("ack")
    String ack;
    @SerializedName("new_link")
    String newLink ;

    public ImageUpdateJson(boolean error, String ack, String newLink) {
        this.error = error;
        this.ack = ack;
        this.newLink = newLink;
    }

    public boolean isError() {
        return error;
    }

    public String getAck() {
        return ack;
    }

    public String getNewLink() {
        return newLink;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setAck(String ack) {
        this.ack = ack;
    }

    public void setNewLink(String newLink) {
        this.newLink = newLink;
    }
}
