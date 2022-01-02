package com.example.spring.controllers;

import com.example.spring.service.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class HelloControler {

    @GetMapping("")
    public ResponseEntity getAllCity(HttpServletRequest req) {
        return new ResponseEntity("Hello", HttpStatus.OK);
    }
}
