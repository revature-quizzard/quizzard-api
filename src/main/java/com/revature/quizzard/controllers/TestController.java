package com.revature.quizzard.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
public class TestController {

    @GetMapping
    public void securityHealthStatus(HttpServletRequest request, HttpServletResponse response){
        System.out.println("Test Succeeded");
        response.setStatus(200);
    }

}
