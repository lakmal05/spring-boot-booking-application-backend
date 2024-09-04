package com.serviceApartment.serviceAparmtnet.controller;
import com.serviceApartment.serviceAparmtnet.dto.customer.CustomerDto;
import com.serviceApartment.serviceAparmtnet.exception.CustomException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {


    @GetMapping("/find-all")
    public String findAll(){
        return "called";
    }

    @PostMapping("/create")
    public  Object create(@Valid @RequestBody CustomerDto data){

        if(data.getAge()>10){
            throw new CustomException("custom excepting called" , HttpStatus.LENGTH_REQUIRED);

        }else{
            return  "success";

        }


    }
}
