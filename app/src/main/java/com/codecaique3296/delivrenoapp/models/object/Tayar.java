package com.codecaique3296.delivrenoapp.models.object;

import com.google.gson.annotations.SerializedName;

public class Tayar {
    @SerializedName("id")
    String id;
    @SerializedName("firstname")
    String firstname;
    @SerializedName("secondname")
    String secondname;
    @SerializedName("phone1")
    String phone1;
    @SerializedName("phone2")
    String phone2;
    @SerializedName("email")
    String email;
    @SerializedName("image")
    String image;

    public Tayar(String id, String firstname, String secondname, String phone1, String phone2, String email, String image) {
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.email = email;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Tayar{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", secondname='" + secondname + '\'' +
                ", phone1='" + phone1 + '\'' +
                ", phone2='" + phone2 + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}