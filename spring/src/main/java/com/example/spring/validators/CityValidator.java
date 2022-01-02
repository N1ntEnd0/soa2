package com.example.spring.validators;

import com.example.spring.DTO.CityDTO;
import com.example.spring.beans.City;
import com.example.spring.beans.Coordinates;
import com.example.spring.beans.Human;
import com.example.spring.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CityValidator {

    public City validate(CityDTO cityDTO) throws ValidationException {
        try {
            Coordinates coordinates = new Coordinates(cityDTO.getCoordinates().getX(), cityDTO.getCoordinates().getY());
            Human human = new Human(cityDTO.getGovernor().getName());
            return new City(cityDTO.getName(), coordinates, cityDTO.getArea(), cityDTO.getPopulation(),
                    cityDTO.getMetersAboveSeaLevel(), cityDTO.getTimezone(), cityDTO.isCapital(), cityDTO.getGovernment(),
                    human);
        } catch (NullPointerException e) {
            throw new ValidationException("В теле запроса не может присутствовать null", HttpStatus.BAD_REQUEST);
        }
    }
}
