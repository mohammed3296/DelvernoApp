package com.codecaique3296.delivrenoapp.models.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Resturant {
    @SerializedName("id")
    String id ;
    @SerializedName("longitude")
    String longitude;
    @SerializedName("latitude")
    String Latitude;
    @SerializedName("name")
    String name;
    @SerializedName("phones")
    List<Phone> phones;
    @SerializedName("address")
    String address;
    @SerializedName("image")
    String image ;

    public Resturant(String id, String longitude, String latitude, String name, List<Phone> phones, String address, String image) {
        this.id = id;
        this.longitude = longitude;
        Latitude = latitude;
        this.name = name;
        this.phones = phones;
        this.address = address;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Resturant{" +
                "id='" + id + '\'' +
                ", longitude='" + longitude + '\'' +
                ", Latitude='" + Latitude + '\'' +
                ", name='" + name + '\'' +
                ", phones=" + phones +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
