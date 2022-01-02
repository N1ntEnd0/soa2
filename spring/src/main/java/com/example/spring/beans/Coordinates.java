package com.example.spring.beans;

import com.example.spring.exceptions.ValidationException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Entity
@Table(name = "coordinates")
public class Coordinates {

    @Id
    @GeneratedValue
    private Long id;

    private Integer x; //Значение поля должно быть больше -147, Поле не может быть null
    private double y; //Значение поля должно быть больше -782

//    public Coordinates(JSONObject json) throws ValidationException {
//        try {
//            this.setX(json.get("x"));
//            this.setY(json.get("y"));
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//            throw new ValidationException("Ошибка сигнатуры запроса. Неверны тип переменной", HttpStatus.BAD_REQUEST);
//        }
//    }

    public Coordinates(Integer x, double y) throws ValidationException {
        this.setX(x);
        this.setY(y);
    }

    public Coordinates() {

    }

    public Long getId() {
        return id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) throws ValidationException {
        try {
            if (x <= -147) {
                throw new ValidationException("поле x должно быть больше -147", HttpStatus.BAD_REQUEST);
            }
            this.x = x;
        } catch (ClassCastException | NullPointerException e) {
            throw new ValidationException("Параметр x должен быть int", HttpStatus.BAD_REQUEST);
        }
    }

    public double getY() {
        return y;
    }

    public void setY(double y) throws ValidationException{
        try {
            if (y <= -782) {
                throw new ValidationException("поле y должно быть больше -782", HttpStatus.BAD_REQUEST);
            }
            this.y = y;
        } catch (ClassCastException e) {
            throw new ValidationException("Параметр y должен быть double", HttpStatus.BAD_REQUEST);
        }
    }
}
