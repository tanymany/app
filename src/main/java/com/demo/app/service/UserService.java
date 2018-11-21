package com.demo.app.service;


import com.demo.app.exceptions.UserAlreadyExistsException;
import com.demo.app.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user) throws UserAlreadyExistsException;
    List<User> getAllUser();
}
