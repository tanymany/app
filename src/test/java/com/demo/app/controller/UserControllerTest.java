package com.demo.app.controller;

import com.demo.app.model.EducationalDetails;
import com.demo.app.model.PersonalDetails;
import com.demo.app.model.User;
import com.demo.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    public MockMvc mockMvc;

    @MockBean
    private User user;

    @MockBean
    private PersonalDetails personalDetails;

    @MockBean
    private EducationalDetails educationalDetails;

    @MockBean
    private Optional optional;

    @MockBean
    private UserService userService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

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
    public void addUserSuccess() throws Exception{
        when(userService.createUser(user)).thenReturn(user);
        mockMvc.perform(post("/api/demo/add")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
