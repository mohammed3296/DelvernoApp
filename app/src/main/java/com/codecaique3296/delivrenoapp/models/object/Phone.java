package com.codecaique3296.delivrenoapp.models.object;

import com.google.gson.annotations.SerializedName;

public class Phone {
    @SerializedName("id")
    String id ;
    @SerializedName("number")
    String number ;

    public Phone(String id, String number) {
        this.id = id;
        this.number = number;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
