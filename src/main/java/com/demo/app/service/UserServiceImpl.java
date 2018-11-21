package com.demo.app.service;

import com.demo.app.exceptions.UserAlreadyExistsException;
import com.demo.app.model.User;
import com.demo.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User userNew;
    @Override
    public  User createUser(User user) throws UserAlreadyExistsException {
        List<User> existingUsers= getAllUser();
        if(existingUsers.size()==0){
            userNew= userRepository.insert(user);
        }
        else{
            for(User existingUser: existingUsers){
                if(existingUser.getPersonalDetails().geteMail().equals(user.getPersonalDetails().geteMail())){
                    throw new UserAlreadyExistsException("User with this Email already exists");
                }
                else {
                    userNew = userRepository.insert(user);
                }
            }
        }
        return userNew;
    }

    @Override
    public List<User> getAllUser() {
        List<User> existingUsers= userRepository.findAll();
        return existingUsers;
    }


}
