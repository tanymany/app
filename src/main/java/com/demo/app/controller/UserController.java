package com.demo.app.controller;

import com.demo.app.exceptions.UserAlreadyExistsException;
import com.demo.app.exceptions.UserNotFoundException;
import com.demo.app.model.User;
import com.demo.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        try {
        if(user==null){
            responseEntity = new ResponseEntity<>("Empty", HttpStatus.BAD_REQUEST);
        }
        else{

                userNew = userService.createUser(user);

            responseEntity = new ResponseEntity<>(userNew, HttpStatus.CREATED);
        }
        } catch (UserAlreadyExistsException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @GetMapping("api/demo/{eMail}")
    public ResponseEntity<?> getUser(@PathVariable String eMail) {
        User foundUser;
        ResponseEntity<?> responseEntity;
        if (eMail == null) {
            responseEntity = new ResponseEntity<>("Empty field", HttpStatus.BAD_REQUEST);
        } else {
            try {
                foundUser = userService.getUserByEmail(eMail);
                responseEntity = new ResponseEntity<>(foundUser, HttpStatus.OK);
            } catch (UserNotFoundException ex) {
                responseEntity = new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            }
        }

        return responseEntity;
    }

    @GetMapping("api/demo/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> foundUsers;
        ResponseEntity<?> responseEntity;
        try {
            foundUsers = userService.getAllUser();
            responseEntity = new ResponseEntity<>(foundUsers, HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            responseEntity = new ResponseEntity<>("No users found!", HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @DeleteMapping("api/demo/remove/{eMail}")
    public ResponseEntity<?> deleteUser(String eMail) {
        ResponseEntity<?> responseEntity;
        try {
            // method userService.deleteUser returns new String ("success!") here
            responseEntity = new ResponseEntity<>(userService.deleteUser(eMail), HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            responseEntity = new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }
}

