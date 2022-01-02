package com.example.lab2.beans;

public class Coordinates {

    private Long id;

    private int x; //Значение поля должно быть больше -147, Поле не может быть null
    private double y; //Значение поля должно быть больше -782

    public Coordinates() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
