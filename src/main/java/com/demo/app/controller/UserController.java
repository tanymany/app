package com.demo.app.controller;

import com.demo.app.exceptions.UserAlreadyExistsException;
import com.demo.app.model.User;
import com.demo.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    User userNew;
    @PostMapping("api/demo/add")
    public ResponseEntity<?> addUser(@RequestBody User user){
        ResponseEntity<?> responseEntity = null;
        if(user==null){
            responseEntity = new ResponseEntity<>("Empty", HttpStatus.BAD_REQUEST);
        }
        else{
            try {
                userNew = userService.createUser(user);
            } catch (UserAlreadyExistsException e) {
                e.getMessage();
            }
            responseEntity = new ResponseEntity<>(userNew, HttpStatus.CREATED);
        }
        return responseEntity;
    }
}
