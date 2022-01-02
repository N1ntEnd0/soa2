package com.example.spring.DTO;

import java.io.Serializable;
import java.util.List;

public class ResponseMessageDTO implements Serializable  {
    private int total;
    private List answer;

    public ResponseMessageDTO(int total, List answer) {
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

    public List getAnswer() {
        return answer;
    }

    public void setAnswer(List answer) {
        this.answer = answer;
    }
}
