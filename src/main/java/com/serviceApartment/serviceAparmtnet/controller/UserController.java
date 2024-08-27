package com.serviceApartment.serviceAparmtnet.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {


    @GetMapping("/find-all")
    public String findAll(){
        return "called";
    }

}
