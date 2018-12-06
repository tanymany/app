package com.demo.app.service;

import com.demo.app.exceptions.UserAlreadyExistsException;
import com.demo.app.exceptions.UserNotFoundException;
import com.demo.app.model.User;
import com.demo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User userNew;

    @Override
    public User createUser(User user) throws UserAlreadyExistsException {
        try {
            List<User> existingUsers = getAllUser();
            for (User existingUser : existingUsers) {
                if (existingUser.getPersonalDetails().geteMail().equals(user.getPersonalDetails().geteMail())) {
                    throw new UserAlreadyExistsException("User with this Email already exists");
                } else {
                    userNew = userRepository.insert(user);
                }
            }
        } catch (UserNotFoundException e) {
            userNew = userRepository.insert(user);
        }
        return userNew;
    }

    @Override
    public List<User> getAllUser() throws UserNotFoundException {
        List<User> existingUsers = userRepository.findAll();
        if (existingUsers.size() == 0) {
            throw new UserNotFoundException("User not found");
        }
        return existingUsers;
    }

    User existingUser;

    @Override
    public User getUserByEmail(String eMail) throws UserNotFoundException {

        try {
            User existingUserFetched = userRepository.findById(eMail).get();
            if (existingUserFetched == null) {
                throw new UserNotFoundException("User not Found");
            } else {
                existingUser = existingUserFetched;
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return existingUser;
    }

    @Override
    public String deleteUser(String eMail) throws UserNotFoundException {
        String flag = null;
        try {
            User user = userRepository.findById(eMail).get();
            if (user == null) {
                throw new UserNotFoundException("User not Found");
            } else {
                userRepository.delete(user);
                flag = "success";
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
        return flag;
    }


}
