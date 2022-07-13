package org.example;

import org.example.dao.ApplicationDao;
import org.example.models.CollegeStudent;
import org.example.models.StudentGrades;
import org.example.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReflectionTestUtils_Test {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    ///////////////////////////////////////////////////////

    @BeforeEach
    public void beforeEach() {

        ReflectionTestUtils.setField(studentOne, "id", 1);
        ReflectionTestUtils.setField(studentOne, "studentGrades",
                new StudentGrades(new ArrayList<>(Arrays.asList(
                        100.00, 85.0, 76.50, 91.75))));

        studentOne.setFirstname("Trent");
        studentOne.setLastname("Lane");
        studentOne.setEmailAddress("trent@mystiksprial.com");
        studentOne.setStudentGrades(studentGrades);
    }

    ///////////////////////////////////////////////////////

    @DisplayName("Andria's FIRST ReflectionTestUtils Test!")
    @Test
    void testGetPrivateField() {
        assertEquals(1, ReflectionTestUtils.getField(studentOne, "id"));
    }

    @DisplayName("Andria's SECOND ReflectionTestUtils Test!")
    @Test
    void testGetPrivateMethod() {
        assertEquals("Trent: 1",
                ReflectionTestUtils.invokeMethod(studentOne, "getFirstnameAndId"),
        "Andria says, 'This should work because ReflectionTestUtils??'");
    }

}