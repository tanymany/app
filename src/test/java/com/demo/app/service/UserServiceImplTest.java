package com.demo.app.service;

import com.demo.app.exceptions.UserAlreadyExistsException;
import com.demo.app.exceptions.UserNotFoundException;
import com.demo.app.model.EducationalDetails;
import com.demo.app.model.PersonalDetails;
import com.demo.app.model.User;
import com.demo.app.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class UserServiceImplTest {
    @MockBean
    private User user;

    @MockBean
    private PersonalDetails personalDetails;

    @MockBean
    private EducationalDetails educationalDetails;

    @MockBean
    private Optional optional;


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        //Personal Details
        personalDetails = new PersonalDetails();
        personalDetails.setName("john");
        personalDetails.setAge(22);
        personalDetails.seteMail("john@abc.com");

        //Educational Details
        educationalDetails = new EducationalDetails();
        educationalDetails.setHighestQualification("B.Tech");
        educationalDetails.setPercentage(75);

        //User
        user = new User();
        user.setPersonalDetails(personalDetails);
        user.setEducationalDetails(educationalDetails);


        optional = Optional.of(user);
    }

    @Test
    public void addUserSuccess(){
        when(userRepository.insert((User)any())).thenReturn(user);
        try {
            User newUser = userServiceImpl.createUser(user);
            Assert.assertEquals(user, newUser);
            verify(userRepository, times(1)).insert((User)any());
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addUserFailure(){
        when(userRepository.insert((User)any())).thenReturn(null);
        try {
            User newUser = userServiceImpl.createUser(user);
            Assert.assertEquals(null, newUser);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserByIdSuccess(){
        when(userRepository.findById("john@abc.com")).thenReturn(optional);
        try {
            User fetchedUser = userServiceImpl.getUserByEmail(user.getPersonalDetails().geteMail());
            Assert.assertEquals(user, fetchedUser);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserByIdFailure() throws UserNotFoundException{
        when(userRepository.findById(user.getPersonalDetails().geteMail())).thenThrow(NoSuchElementException.class);
        User fetchedUser = userServiceImpl.getUserByEmail(user.getPersonalDetails().geteMail());
        Assert.assertEquals(null, fetchedUser);
    }

    @Test
    public void deleteUserSuccess() throws UserNotFoundException{
        when(userRepository.findById(user.getPersonalDetails().geteMail())).thenReturn(optional);
        String flag = userServiceImpl.deleteUser(user.getPersonalDetails().geteMail());
        Assert.assertEquals("success", flag);
    }

    @Test
    public void deleteUserFailure() throws UserNotFoundException{
        when(userRepository.findById(user.getPersonalDetails().geteMail())).thenThrow(NoSuchElementException.class);
        String flag = userServiceImpl.deleteUser(user.getPersonalDetails().geteMail());
        Assert.assertEquals(null, flag);
    }
}
