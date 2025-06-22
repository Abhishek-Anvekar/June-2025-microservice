package com.javaguides.employee_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope // MessageController is nothing but a spring bean and this @RefreshScope will force this spring bean to reload the configuration
@RestController
@RequestMapping("/")
public class MessageController {

    @Value("${spring.boot.message}")
    private String message;

    //For testing /refresh api of the actuator to refresh the config

    @GetMapping("/message")
    public String getMessage(){
        return message;
    }

}
