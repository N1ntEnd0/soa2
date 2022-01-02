package com.example.spring.beans;

import com.example.spring.exceptions.ValidationException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Entity
@Table(name = "human")
public class Human {

    @Id
    @GeneratedValue
    private Long id;

    private String name; //Поле не может быть null, Строка не может быть пустой

//    public Human(JSONObject json) throws ValidationException {
//        try {
//            this.setName((String) json.get("name"));
//        } catch (ClassCastException e) {
//            throw new ValidationException("Параметр Human name должен быть String", HttpStatus.BAD_REQUEST);
//        }
//    }

    public Human(String name) throws ValidationException {
        this.setName(name);
    }

    public Long getId() {
        return id;
    }

    public Human() {}

    public String getName() {
        return name;
    }

    public void setName(String name) throws ValidationException {
        if (name == null) {
            throw new ValidationException("поле name должно быть представлено в запросе", HttpStatus.BAD_REQUEST);
        }
        if (name.equals("")) {
            throw new ValidationException("поле name не может быть пустым", HttpStatus.BAD_REQUEST);
        }
        this.name = name;
    }
}
