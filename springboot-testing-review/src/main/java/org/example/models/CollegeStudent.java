package org.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CollegeStudent implements Student {

    private int id;
    private String firstname;
    private String lastname;
    private String emailAddress;
    private StudentGrades studentGrades;

    public CollegeStudent(String firstname, String lastname, String emailAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
    }

    ///////////////////////////////////////////////

    @Override
    public String studentInformation() {
        return getFullName() + " " + getEmailAddress();
    }

    @Override
    public String getFullName() {
        return getFirstname() + " " + getLastname();
    }

    ///////////////////////////////////////////////

    private String getFirstnameAndId() {
        return getFirstname() + ": " + getId();
    }

}