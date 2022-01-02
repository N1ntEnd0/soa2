package com.example.spring.controllers;

import com.example.spring.DTO.CityDTO;
import com.example.spring.DTO.ResponseMessageDTO;
import com.example.spring.beans.City;
import com.example.spring.exceptions.CityNotFoundException;
import com.example.spring.exceptions.ValidationException;
import com.example.spring.repository.CityRepository;
import com.example.spring.service.CityRepositoryService;
import com.example.spring.service.Utils;
import com.example.spring.validators.CityValidator;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@RestController
@RequestMapping("/city")
public class CityController {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CityRepositoryService cityRepositoryService;

    @Autowired
    CityValidator cityValidator;

    @GetMapping("/")
    public ResponseEntity getAllCity(HttpServletRequest req) {
        return new ResponseEntity(Utils.pagination(cityRepositoryService.findAll(req), req), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getCity(@PathVariable Long id) {
        try {
            return new ResponseEntity(cityRepositoryService.getCityById(id), HttpStatus.OK);
        } catch (CityNotFoundException e) {
            ResponseMessageDTO messageDTO = new ResponseMessageDTO();
            messageDTO.setAnswer(e.getErrMessage());
            return new ResponseEntity(messageDTO, e.getErrStatus());
        }
    }

    @PostMapping("/")
    public ResponseEntity createCity(@RequestBody CityDTO city1) {
        try {
            cityRepositoryService.create(cityValidator.validate(city1));
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (ValidationException e) {
            ResponseMessageDTO messageDTO = new ResponseMessageDTO();
            messageDTO.setAnswer(e.getErrMessage());
            return new ResponseEntity(messageDTO, e.getErrStatus());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCity(@PathVariable Long id) {
        try {
            cityRepositoryService.deleteCity(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (CityNotFoundException e) {
            ResponseMessageDTO messageDTO = new ResponseMessageDTO();
            messageDTO.setAnswer(e.getErrMessage());
            return new ResponseEntity(messageDTO, e.getErrStatus());
        }
    }

    @PutMapping("/")
    public ResponseEntity updateCity(@RequestBody CityDTO cityDTO) {
        try {
            cityRepositoryService.updateCity(cityDTO);
            return new ResponseEntity(HttpStatus.OK);
        } catch (CityNotFoundException e) {
            ResponseMessageDTO messageDTO = new ResponseMessageDTO();
            messageDTO.setAnswer(e.getErrMessage());
            return new ResponseEntity(messageDTO, e.getErrStatus());
        } catch (ValidationException e) {
            ResponseMessageDTO messageDTO = new ResponseMessageDTO();
            messageDTO.setAnswer(e.getErrMessage());
            return new ResponseEntity(messageDTO, e.getErrStatus());
        }
    }

    @GetMapping("/area/max")
    public ResponseEntity getCityWithMaxArea() {
        return new ResponseEntity(cityRepositoryService.getWithMaxArea(), HttpStatus.OK);
    }

    @GetMapping("/area/min")
    public ResponseEntity getCityWithMinArea() {
        return new ResponseEntity(cityRepositoryService.getWithMinArea(), HttpStatus.OK);
    }

    @GetMapping("/population/max")
    public ResponseEntity getCityWithMaxPopulation() {
        return new ResponseEntity(cityRepositoryService.getWithMaxPopulation(), HttpStatus.OK);
    }


    @GetMapping("/group")
    public ResponseEntity getGroupCity() {
        return new ResponseEntity(cityRepositoryService.getCityGroup(), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity deleteCityByCapital(@RequestParam Boolean capital) {
        try {
            System.out.println(capital);
            cityRepositoryService.deleteCityByCapital(capital);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (CityNotFoundException e) {
            ResponseMessageDTO messageDTO = new ResponseMessageDTO();
            messageDTO.setAnswer(e.getErrMessage());
            return new ResponseEntity(messageDTO, e.getErrStatus());
        }
    }


//@Path("/hello-world")
//public class HelloResource {
//    @GET
//    @Produces("text/plain")
//    public Object hello() {
//        Client client = ClientBuilder.newClient();
//        WebTarget target = client.target("http://localhost:8081/city/{id}").resolveTemplate("id", 2);
//        Response s = target.request().get();
//        System.out.println("sss");
//        System.out.println(s);
//        return s.getEntity();
//    }
//}
}
