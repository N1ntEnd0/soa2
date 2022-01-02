package com.example.lab2.beans;

import java.io.Serializable;
import java.util.List;

public class ResponseMessageDTO implements Serializable  {
    private int total;
    private List<City> answer;

    public ResponseMessageDTO(int total, List<City> answer) {
        this.total = total;
        this.answer = answer;
    }

    public ResponseMessageDTO() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public City getAnswer() {
        return answer.get(0);
//        String str = answer.toString();
//        return str.substring(0, str.length());
    }

    public void setAnswer(List<City> answer) {
        this.answer = answer;
    }
}
