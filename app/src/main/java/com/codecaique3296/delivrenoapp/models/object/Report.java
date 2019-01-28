package com.codecaique3296.delivrenoapp.models.object;

import com.google.gson.annotations.SerializedName;

public class Report {
    @SerializedName("id")
    String id;
    @SerializedName("resturant_name")
    String resturant_name;
    @SerializedName("client_address")
    String client_address;
    @SerializedName("res_address")
    String res_address;
    @SerializedName("receipttime")
    String receipttime;
    @SerializedName("deliveredtime")
    String deliveredtime;

    public Report(String id, String resturant_name,
                  String client_address, String res_address, String receipttime, String deliveredtime) {
        this.id = id;
        this.resturant_name = resturant_name;
        this.client_address = client_address;
        this.res_address = res_address;
        this.receipttime = receipttime;
        this.deliveredtime = deliveredtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResturant_name() {
        return resturant_name;
    }

    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public String getRes_address() {
        return res_address;
    }

    public void setRes_address(String res_address) {
        this.res_address = res_address;
    }

    public String getReceipttime() {
        return receipttime;
    }

    public void setReceipttime(String receipttime) {
        this.receipttime = receipttime;
    }

    public String getDeliveredtime() {
        return deliveredtime;
    }

    public void setDeliveredtime(String deliveredtime) {
        this.deliveredtime = deliveredtime;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id='" + id + '\'' +
                ", resturant_name='" + resturant_name + '\'' +
                ", client_address='" + client_address + '\'' +
                ", res_address='" + res_address + '\'' +
                ", receipttime='" + receipttime + '\'' +
                ", deliveredtime='" + deliveredtime + '\'' +
                '}';
    }
}
