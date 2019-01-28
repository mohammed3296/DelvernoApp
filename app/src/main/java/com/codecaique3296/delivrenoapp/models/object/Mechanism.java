package com.codecaique3296.delivrenoapp.models.object;

import com.google.gson.annotations.SerializedName;

public class Mechanism {

    @SerializedName("content")
    String content ;

    public Mechanism(String content) {

        this.content = content;
    }


    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Mechanism{" +
                ", content='" + content + '\'' +
                '}';
    }
}
