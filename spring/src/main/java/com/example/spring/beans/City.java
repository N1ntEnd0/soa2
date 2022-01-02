package com.example.spring.beans;

import com.example.spring.exceptions.ValidationException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer area; //Значение поля должно быть больше 0, Поле не может быть null
    private int population; //Значение поля должно быть больше 0
    private Integer metersAboveSeaLevel;
    private float timezone; //Значение поля должно быть больше -13, Максимальное значение поля: 15
    private boolean capital;
    @Enumerated(EnumType.STRING)
    private Government government; //Поле не может быть null
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "human_id")
    private Human governor; //Поле не может быть null

//    public City(JSONObject json) throws ValidationException {
//        try {
//            this.setName(json.get("name"));
//            Coordinates coordinates = new Coordinates((JSONObject) json.get("coordinates"));
//            this.setCoordinates(coordinates);
//            this.setArea(json.get("area"));
//            this.setPopulation(json.get("population"));
//            this.setMetersAboveSeaLevel(json.get("metersAboveSeaLevel"));
//            this.setTimezone(json.get("timezone"));
//            this.setCapital(json.get("capital"));
//            this.setGovernment(json.get("government"));
//            Human human = new Human((JSONObject) json.get("governor"));
//            this.setGovernor(human);
//            this.setCreationDate(LocalDateTime.now());
//        } catch (ClassCastException e) {
//            e.printStackTrace();
////todo      exception
////            throw new ValidationException("Ошибка сигнатуры запроса. Неверны тип переменной", 400);
//        }
//    }

    public City(String name, Coordinates coordinates, Integer area, int population, Integer metersAboveSeaLevel,
                float timezone, Boolean capital, Government government, Human human) throws ValidationException {
        this.setName(name);
        this.setCoordinates(coordinates);
        this.setArea(area);
        this.setPopulation(population);
        this.setMetersAboveSeaLevel(metersAboveSeaLevel);
        this.setTimezone(timezone);
        this.setCapital(capital);
        this.setGovernment(government);
        this.setGovernor(human);
        this.setCreationDate(LocalDateTime.now());
    }



    public void setCreationDate(LocalDateTime creationDate) throws ValidationException {
        if (creationDate == null) {
            throw new ValidationException("поле creationDate должно быть представлено в запросе", HttpStatus.BAD_REQUEST);
        }
        this.creationDate = creationDate;
    }

    public City(){}

    public void setName(String name) throws ValidationException {
        try {
            if (name.equals("")) {
                throw new ValidationException("поле name не может быть пустым", HttpStatus.BAD_REQUEST);
            }
            this.name = name;
        } catch (ClassCastException | NullPointerException e) {
            throw new ValidationException("Параметр name должен быть String", HttpStatus.BAD_REQUEST);
        }
    }

    public void setCoordinates(Coordinates coordinates) throws ValidationException {
        try {
            if (coordinates == null) {
                throw new ValidationException("поле coordinates не соответствует условиям валидации", HttpStatus.BAD_REQUEST);
            }
            this.coordinates = coordinates;
        } catch (NullPointerException e) {
            throw new ValidationException("coordinates не может быть null", HttpStatus.BAD_REQUEST);
        }
    }

    public void setArea(Integer area) throws ValidationException{
        try {
            if (area <= 0){
                throw new ValidationException("поле area должно быть больше 0", HttpStatus.BAD_REQUEST);
            }
            this.area = area;
        }catch (ClassCastException | NullPointerException e){
            throw new ValidationException("Параметр area должен быть Integer", HttpStatus.BAD_REQUEST);
        }
    }

    public void setPopulation(Integer population) throws ValidationException {
        try {
            if (population <= 0){
                throw new ValidationException("поле population должно быть больше 0", HttpStatus.BAD_REQUEST);
            }
            this.population = population;
        } catch (ClassCastException | NullPointerException e){
            throw new ValidationException("Параметр population должен быть int", HttpStatus.BAD_REQUEST);
        }
    }

    public void setMetersAboveSeaLevel(Integer metersAboveSeaLevel) throws ValidationException {
        try {
            if (metersAboveSeaLevel == null) {
                this.metersAboveSeaLevel = 0;
            } else {
                this.metersAboveSeaLevel = metersAboveSeaLevel;
            }
        } catch (ClassCastException | NullPointerException e){
            throw new ValidationException("Параметр metersAboveSeaLevel должен быть Integer", HttpStatus.BAD_REQUEST);
        }
    }

    public void setTimezone(Float timezone) throws ValidationException {
        try {
            if (timezone <= -13) {
                throw new ValidationException("поле timezone должно быть больше -13", HttpStatus.BAD_REQUEST);
            }
            if (timezone > 15) {
                throw new ValidationException("поле timezone должно быть меньше 15", HttpStatus.BAD_REQUEST);
            }
            this.timezone = timezone;
        } catch (ClassCastException | NullPointerException e){
            throw new ValidationException("Параметр timezone должен быть float", HttpStatus.BAD_REQUEST);
        }
    }

    public void setCapital(Boolean capital) throws ValidationException {
        try {
            this.capital = capital;
        } catch (ClassCastException | NullPointerException e){
            throw new ValidationException("Параметр capital должен быть boolean", HttpStatus.BAD_REQUEST);
        }
    }

    public void setGovernment(Government governmentName) throws ValidationException {
        try {
            if (governmentName == null) {
                throw new ValidationException("поле government должно быть представлено в запросе", HttpStatus.BAD_REQUEST);
            }
            for (Government government : Government.values()) {
                if (government.equals(governmentName)) {
                    this.government = government;
                    return;
                }
            }
            throw new ValidationException("Поле government не соответствует заданным значениям", HttpStatus.BAD_REQUEST);
        } catch (ClassCastException | NullPointerException e){
            throw new ValidationException("Параметр government должен быть String", HttpStatus.BAD_REQUEST);
        }
    }

    public void setGovernor(Human governor) throws ValidationException {
        if (governor == null){
            throw new ValidationException("поле governor не соответствует условиям валидации", HttpStatus.BAD_REQUEST);
        }
        this.governor = governor;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Integer getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }

    public Integer getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public float getTimezone() {
        return timezone;
    }

    public boolean isCapital() {
        return capital;
    }

    public Government getGovernment() {
        return government;
    }

    public Human getGovernor() {
        return governor;
    }
}
