package com.example.lab2.beans;

public class Human {

    private Long id;
    private String name; //Поле не может быть null, Строка не может быть пустой

    public Human() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
