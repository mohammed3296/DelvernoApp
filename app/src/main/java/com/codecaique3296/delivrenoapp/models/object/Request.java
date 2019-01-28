package com.codecaique3296.delivrenoapp.models.object;

import com.google.gson.annotations.SerializedName;

public class Request {
    @SerializedName("id")
    String id;
    @SerializedName("resturant_id")
    String resturant_id;
    @SerializedName("resturant_address")
    String resturant_address;
    @SerializedName("resturant_name")
    String resturant_name;
    @SerializedName("tayar_id")
    String tayar_id;
    @SerializedName("client_address")
    String client_address;
    @SerializedName("accepted")
    String accepted;
    @SerializedName("receipt")
    String receipt;
    @SerializedName("delivered")
    String delivered;
    @SerializedName("receipttime")
    String receipttime;
    @SerializedName("deliveredtime")
    String deliveredtime;
    @SerializedName("time")
    String time;

    public Request(String id, String resturant_id, String resturant_address, String resturant_name,
                   String tayar_id, String client_address, String accepted,
                   String receipt, String delivered, String receipttime, String deliveredtime, String time) {
        this.id = id;
        this.resturant_id = resturant_id;
        this.resturant_address = resturant_address;
        this.resturant_name = resturant_name;
        this.tayar_id = tayar_id;
        this.client_address = client_address;
        this.accepted = accepted;
        this.receipt = receipt;
        this.delivered = delivered;
        this.receipttime = receipttime;
        this.deliveredtime = deliveredtime;
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setResturant_id(String resturant_id) {
        this.resturant_id = resturant_id;
    }

    public void setResturant_address(String resturant_address) {
        this.resturant_address = resturant_address;
    }

    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }

    public void setTayar_id(String tayar_id) {
        this.tayar_id = tayar_id;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }

    public void setReceipttime(String receipttime) {
        this.receipttime = receipttime;
    }

    public void setDeliveredtime(String deliveredtime) {
        this.deliveredtime = deliveredtime;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getResturant_id() {
        return resturant_id;
    }

    public String getResturant_address() {
        return resturant_address;
    }

    public String getResturant_name() {
        return resturant_name;
    }

    public String getTayar_id() {
        return tayar_id;
    }

    public String getClient_address() {
        return client_address;
    }

    public String getAccepted() {
        return accepted;
    }

    public String getReceipt() {
        return receipt;
    }

    public String getDelivered() {
        return delivered;
    }

    public String getReceipttime() {
        return receipttime;
    }

    public String getDeliveredtime() {
        return deliveredtime;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", resturant_id='" + resturant_id + '\'' +
                ", resturant_address='" + resturant_address + '\'' +
                ", resturant_name='" + resturant_name + '\'' +
                ", tayar_id='" + tayar_id + '\'' +
                ", client_address='" + client_address + '\'' +
                ", accepted='" + accepted + '\'' +
                ", receipt='" + receipt + '\'' +
                ", delivered='" + delivered + '\'' +
                ", receipttime='" + receipttime + '\'' +
                ", deliveredtime='" + deliveredtime + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
