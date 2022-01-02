package com.example.spring.service;

import com.example.spring.DTO.ResponseMessageDTO;
import com.example.spring.beans.City;
import com.example.spring.beans.Coordinates;
import com.example.spring.beans.Government;
import com.example.spring.beans.Human;
import com.example.spring.exceptions.ValidationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {
    public static JSONObject getJSONFromBody(HttpServletRequest request) throws ValidationException {
        try {
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            return (JSONObject) new JSONParser().parse(body);
        }catch (ParseException | IOException e){
            throw new ValidationException("Ошибка сигнатуры запроса. Неверный формат JSON", HttpStatus.BAD_REQUEST);
        }
    }

    public static ArrayList<String> filterCityParams(HttpServletRequest req){
        ArrayList<String> params = fillCityParamsList();
        ArrayList<String> result = new ArrayList<>();
        for (String param: params){
            String predict_parameter = req.getParameter(param);
            if (predict_parameter == null){
                continue;
            }
            System.out.println(param);
            result.add(param);
        }
        return result;
    }

    public static ArrayList<String> fillCityParamsList(){
        ArrayList<String> result = new ArrayList<>();

        result.add("id");
        result.add("name");

        result.add("coordinates_x");
        result.add("coordinates_y");
        result.add("coordinates_id");

        result.add("creationDate");

        result.add("area");
        result.add("population");
        result.add("metersAboveSeaLevel");
        result.add("timezone");
        result.add("capital");

        result.add("government");

        result.add("human_id");
        result.add("human_name");

        return result;
    }
    public static ArrayList<City> sortByField(ArrayList<City> cities, ArrayList<String> params){
        for (String param : params){
            try {
                switch (param) {
                    case "id":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::getId)).collect(Collectors.toList());
                        break;
                    case "name":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::getName)).collect(Collectors.toList());
                        break;
                    case "population":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::getPopulation)).collect(Collectors.toList());
                        break;
                    case "coordinates_id":
                        cities = (ArrayList<City>) cities.stream().sorted(
                                Comparator.comparing(city -> city.getCoordinates().getId())
                        ).collect(Collectors.toList());
                        break;
                    case "coordinates_x":
                        cities = (ArrayList<City>) cities.stream().sorted(
                                Comparator.comparing(city -> city.getCoordinates().getX())
                        ).collect(Collectors.toList());
                        break;
                    case "coordinates_y":
                        cities = (ArrayList<City>) cities.stream().sorted(
                                Comparator.comparing(city -> city.getCoordinates().getY())
                        ).collect(Collectors.toList());
                        break;
                    case "creationDate":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::getCreationDate)).collect(Collectors.toList());
                        break;
                    case "area":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::getArea)).collect(Collectors.toList());
                        break;
                    case "metersAboveSeaLevel":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::getMetersAboveSeaLevel)).collect(Collectors.toList());
                        break;
                    case "timezone":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::getTimezone)).collect(Collectors.toList());
                        break;
                    case "capital":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::isCapital)).collect(Collectors.toList());
                        break;
                    case "government":
                        cities = (ArrayList<City>) cities.stream().sorted(Comparator.comparing(City::getGovernment)).collect(Collectors.toList());
                        break;
                    case "human_id":
                        cities = (ArrayList<City>) cities.stream().sorted(
                                Comparator.comparing(city -> city.getGovernor().getId())
                        ).collect(Collectors.toList());
                        break;
                    case "human_name":
                        cities = (ArrayList<City>) cities.stream().sorted(
                                Comparator.comparing(city -> city.getGovernor().getName())
                        ).collect(Collectors.toList());
                        break;
                }
            }catch (ClassCastException | IllegalArgumentException e){
                cities.clear();
                return cities;
            }
        }
        return cities;
    }

    public static HashMap<String, Object> createHashMapFilterParams(ArrayList<String> params, HttpServletRequest request){
        HashMap<String, Object> result = new HashMap<>();
        for (String name: params){
            result.put(name, request.getParameter(name));
        }
        return result;
    }

    public static ArrayList<City> filterByField(ArrayList<City> list, HashMap<String,Object> map){
        for (Map.Entry<String, Object> entry : map.entrySet()){
            String key = entry.getKey();
            String value = (String) entry.getValue();
            System.out.println("filtering " + key + ": " + value);
            try {
                switch (key) {
                    case "id":
                        list = (ArrayList<City>) list.stream().filter(city -> city.getId() == Long.parseLong(value)).collect(Collectors.toList());
                        break;
                    case "name":
                        list = (ArrayList<City>) list.stream().filter(city -> city.getName().equals(value)).collect(Collectors.toList());
                        break;
                    case "coordinates_id":
                        list = (ArrayList<City>) list.stream().filter(city -> {
                            Coordinates coordinates = city.getCoordinates();
                            return coordinates.getId() == Long.parseLong(value);
                        }).collect(Collectors.toList());
                        break;
                    case "coordinates_x":
                        list = (ArrayList<City>) list.stream().filter(city -> {
                            Coordinates coordinates = city.getCoordinates();
                            return coordinates.getX() == Integer.parseInt(value);
                        }).collect(Collectors.toList());
                        break;
                    case "coordinates_y":
                        list = (ArrayList<City>) list.stream().filter(city -> {
                            Coordinates coordinates = city.getCoordinates();
                            return coordinates.getY() == Double.parseDouble(value);
                        }).collect(Collectors.toList());
                        break;
                    case "creationDate":
                        list = (ArrayList<City>) list.stream().filter(city -> city.getCreationDate().truncatedTo(ChronoUnit.MINUTES).equals(LocalDateTime.parse(value))).collect(Collectors.toList());
                        break;
                    case "area":
                        list = (ArrayList<City>) list.stream().filter(city -> city.getArea() == Integer.parseInt(value)).collect(Collectors.toList());
                        break;
                    case "population":
                        list = (ArrayList<City>) list.stream().filter(city -> city.getPopulation() == Integer.parseInt(value)).collect(Collectors.toList());
                        break;
                    case "metersAboveSeaLevel":
                        list = (ArrayList<City>) list.stream().filter(city -> city.getMetersAboveSeaLevel() == Integer.parseInt(value)).collect(Collectors.toList());
                        break;
                    case "timezone":
                        list = (ArrayList<City>) list.stream().filter(city -> city.getTimezone() == Float.parseFloat(value)).collect(Collectors.toList());
                        break;
                    case "capital":
                        list = (ArrayList<City>) list.stream().filter(city -> city.isCapital() == Boolean.parseBoolean(value)).collect(Collectors.toList());
                        break;
                    case "government":
                        list = (ArrayList<City>) list.stream().filter(city -> city.getGovernment() == Government.valueOf(value)).collect(Collectors.toList());
                        break;
                    case "human_id":
                        list = (ArrayList<City>) list.stream().filter(city -> {
                            Human governor = city.getGovernor();
                            System.out.println(governor);
                            System.out.println(governor.getName());
                            return governor.getId() == Long.parseLong(value);
                        }).collect(Collectors.toList());
                        break;
                    case "human_name":
                        list = (ArrayList<City>) list.stream().filter(city -> {
                            Human governor = city.getGovernor();
                            return governor.getName().equals(value);
                        }).collect(Collectors.toList());
                        break;
                }
            }catch (ClassCastException | IllegalArgumentException e){
                list.clear();
                return list;
            }
        }
        return list;
    }

    public static ResponseMessageDTO pagination(ArrayList<City> list, HttpServletRequest request) {
        if (request.getParameter("page") == null | request.getParameter("pageSize") == null) {
            return new ResponseMessageDTO(list.size(), list);
        }

        int page = Integer.parseInt(request.getParameter("page"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        if (page <= 0 || pageSize <= 0) {
            return new ResponseMessageDTO(list.size(), list);
        }

        int fromIndex = (page - 1) * pageSize;
        if (list == null || list.size() <= fromIndex) {
            return new ResponseMessageDTO(0, Collections.emptyList());
        }

        return new ResponseMessageDTO(list.size(), list.subList(fromIndex, Math.min(fromIndex + pageSize, list.size())));
    }
}
