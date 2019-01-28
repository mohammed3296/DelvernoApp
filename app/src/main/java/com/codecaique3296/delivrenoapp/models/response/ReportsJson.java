package com.codecaique3296.delivrenoapp.models.response;

import com.codecaique3296.delivrenoapp.models.object.Report;
import com.codecaique3296.delivrenoapp.models.object.Resturant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportsJson {
    @SerializedName("error")
    boolean error;
    @SerializedName("ack")
    String ack;
    @SerializedName("reports")
    List<Report> reports ;

    public ReportsJson(boolean error, String ack, List<Report> reports) {
        this.error = error;
        this.ack = ack;
        this.reports = reports;
    }

    public boolean isError() {
        return error;
    }

    public String getAck() {
        return ack;
    }

    public List<Report> getReports() {
        return reports;
    }
}
