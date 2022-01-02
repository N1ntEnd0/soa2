package com.example.spring.service;

import com.example.spring.DTO.CityDTO;
import com.example.spring.DTO.ResponseMessageDTO;
import com.example.spring.beans.City;
import com.example.spring.exceptions.CityNotFoundException;
import com.example.spring.exceptions.ValidationException;
import com.example.spring.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CityRepositoryService {

    @Autowired
    CityRepository cityRepository;

    public ArrayList<City> findAll(HttpServletRequest req) {

        ArrayList<String> params = Utils.filterCityParams(req);
        ArrayList<City> cities = cityRepository.findAll();
        if (params.size() == 0){
            return cities;
        }

        if (req.getParameter("sort") != null) {
            return Utils.sortByField(cities, params);
        }

        HashMap<String, Object> filter_params = Utils.createHashMapFilterParams(params, req);
        return Utils.filterByField(cities, filter_params);
    }

    public ResponseMessageDTO getCityById(Long id) throws CityNotFoundException {
        List arr = cityRepository.getCityById(id);
        if (arr.size() == 0) {
            throw new CityNotFoundException("Города с таким id не существует!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseMessageDTO(arr.size(), arr);
    }

    public void create(City city) {
        cityRepository.save(city);
    }

    public void deleteCity(Long id) throws CityNotFoundException {
        try {
            cityRepository.delete(cityRepository.findCityById(id).get());
        } catch (NoSuchElementException e) {
            throw new CityNotFoundException("Города с таким id не существует!", HttpStatus.BAD_REQUEST);
        }
    }

    public void updateCity(CityDTO cityDTO) throws CityNotFoundException, ValidationException {
        if (cityDTO.getId() != null) {
            try {
                City city = cityRepository.findCityById(cityDTO.getId()).get();

                if (cityDTO.getName() != null) {
                    city.setName(cityDTO.getName());
                }

                if (cityDTO.getCoordinates() != null) {
                    if (cityDTO.getCoordinates().getX() != null) {
                        city.getCoordinates().setX(cityDTO.getCoordinates().getX());
                    }
                    if (cityDTO.getCoordinates().getY() != null) {
                        city.getCoordinates().setY(cityDTO.getCoordinates().getY());
                    }
                }

                if (cityDTO.getArea() != null) {
                    city.setArea(cityDTO.getArea());
                }

                if (cityDTO.getPopulation() != null) {
                    city.setPopulation(cityDTO.getPopulation());
                }

                if (cityDTO.getMetersAboveSeaLevel() != null) {
                    city.setMetersAboveSeaLevel(cityDTO.getMetersAboveSeaLevel());
                }

                if (cityDTO.getTimezone() != null) {
                    city.setTimezone(cityDTO.getTimezone());
                }

                if (cityDTO.isCapital() != null) {
                    city.setCapital(cityDTO.isCapital());
                }

                if (cityDTO.getGovernment() != null) {
                    city.setGovernment(cityDTO.getGovernment());
                }

                if (cityDTO.getGovernor() != null && cityDTO.getGovernor().getName() != null) {
                    city.getGovernor().setName(cityDTO.getGovernor().getName());
                }

                cityRepository.save(city);
            } catch (NoSuchElementException e) {
                throw new CityNotFoundException("Города с таким id не существует!", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new CityNotFoundException("Города с таким id не существует!", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseMessageDTO getWithMaxArea() {
        List list = cityRepository.findFirstByOrderByAreaDesc();
        return new ResponseMessageDTO(list.size(), list);
    }

     public ResponseMessageDTO getWithMinArea() {
        List list = cityRepository.findFirstByOrderByAreaAsc();
        return new ResponseMessageDTO(list.size(), list);
    }

    public ResponseMessageDTO getWithMaxPopulation() {
        List list = cityRepository.findFirstByOrderByPopulationDesc();
        return new ResponseMessageDTO(list.size(), list);
    }


    public HashMap<String, Long> getCityGroup() {

        HashMap<String, Long> map = new HashMap<>();
        List<String> l = cityRepository.getCitiesGroup();
        List<Long> l2 = cityRepository.getCountCitiesGroup();
        for (int i = 0; i < l.size(); i++) {
            map.put(l.get(i), l2.get(i));
        }
        return map;
    }

    public void deleteCityByCapital(Boolean capital) throws CityNotFoundException {
        try {
            if (cityRepository.deleteCitiesByCapital(capital) <= 0) {
                 throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            throw new CityNotFoundException("Города с таким capital не существует!", HttpStatus.BAD_REQUEST);
        }
    }
}
