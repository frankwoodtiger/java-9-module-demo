package com.hello.model;

import java.util.Date;

public class HelloMessageModel {
    private String message;
    private Date date;

    public HelloMessageModel(String message) {
        this.message = message;
        this.date = new Date();
    }

    public String getMessage() {
        return this.message;
    }

    public Date getDate() {
        return this.date;
    }
}
