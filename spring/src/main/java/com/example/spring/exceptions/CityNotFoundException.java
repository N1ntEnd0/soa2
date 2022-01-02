package com.example.spring.exceptions;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class CityNotFoundException extends Throwable {
    private String errMessage;
    private HttpStatus errStatus;

    public CityNotFoundException(String errMessage, HttpStatus errStatus) {
        super();
        this.errMessage = errMessage;
        this.errStatus = errStatus;
    }

//    public String getErrMessage() {
//        return errMessage;
//    }
    public ArrayList getErrMessage() {
        ArrayList arr = new ArrayList();
        arr.add(errMessage);
        return arr;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public HttpStatus getErrStatus() {
        return errStatus;
    }

    public void setErrStatus(HttpStatus errStatus) {
        this.errStatus = errStatus;
    }
}
