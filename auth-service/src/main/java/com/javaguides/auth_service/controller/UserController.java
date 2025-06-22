package com.javaguides.auth_service.controller;

import com.javaguides.auth_service.dto.UserDto;
import com.javaguides.auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add-user")
    ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/")
    ResponseEntity<List<UserDto>> getAllUser(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    ResponseEntity<UserDto> getUserById(@PathVariable long userId){
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    ResponseEntity<UserDto> updateUserById(@PathVariable long userId, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.updateUserById(userId,userDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    ResponseEntity<String> deleteUserById(@PathVariable long userId){
        return new ResponseEntity<>(userService.deleteUserById(userId),HttpStatus.OK);
    }
}
