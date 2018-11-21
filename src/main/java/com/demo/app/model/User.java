package com.demo.app.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class User {

    @Id
    private String uuid = UUID.randomUUID().toString();
    private PersonalDetails personalDetails;
    private EducationalDetails educationalDetails;

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public EducationalDetails getEducationalDetails() {
        return educationalDetails;
    }

    public void setEducationalDetails(EducationalDetails educationalDetails) {
        this.educationalDetails = educationalDetails;
    }
}
