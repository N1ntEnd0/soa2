package com.example.spring.DTO;

import com.example.spring.beans.Coordinates;
import com.example.spring.beans.Government;
import com.example.spring.beans.Human;

import javax.persistence.*;

public class CityDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String name;
    private CoordinatesDTO coordinates;
    private Integer area;
    private Integer population;
    private Integer metersAboveSeaLevel;
    private Float timezone;
    private Boolean capital;
    private Government government;
    private HumanDTO governor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordinatesDTO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesDTO coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(Integer metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public Float getTimezone() {
        return timezone;
    }

    public void setTimezone(Float timezone) {
        this.timezone = timezone;
    }

    public Boolean isCapital() {
        return capital;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public HumanDTO getGovernor() {
        return governor;
    }

    public void setGovernor(HumanDTO governor) {
        this.governor = governor;
    }
}
