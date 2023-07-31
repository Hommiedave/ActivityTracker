package com.example.activitytracker.controllers;


import com.example.activitytracker.models.request.LoginRequest;
import com.example.activitytracker.models.request.UserRequest;
import com.example.activitytracker.services.UserService;
import com.example.activitytracker.utils.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping()
    public ResponseEntity<GenericResponse> signUp(@RequestBody UserRequest userRequest){
        GenericResponse genericResponse = userService.createUser(userRequest);
        return new ResponseEntity<>(genericResponse, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<GenericResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        GenericResponse genericResponse = userService.login(loginRequest, httpServletRequest);
        return new ResponseEntity<>(genericResponse, genericResponse.getHttpStatus());
    }
}
