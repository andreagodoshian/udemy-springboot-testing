package org.example;

import org.example.models.CollegeStudent;
import org.example.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Rigorous test for simple App :-)
 */
@SpringBootTest()
public class AppTest {

    @Autowired
    ApplicationContext context;

    private static int count = 0;

    @Value("${info.school.name}")
    private String schoolName;

    @Value("${info.app.name}")
    private String appName;

    @Value("${info.app.description}")
    private String appDescription;

    @Value("${info.app.version}")
    private String appVersion;

    @Autowired
    CollegeStudent collegeStudent;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach() {
        count = count + 1;
        System.out.println("Testing: " + appName +
                "\nDescription: " + appDescription +
                "\nVersion: " + appVersion +
                "\nExecution number: " + count);

        collegeStudent.setFirstname("Daria");
        collegeStudent.setLastname("Morgendorffer");
        collegeStudent.setEmailAddress("daria@sicksadworld.com");
        studentGrades.setMathGradeResults(
                new ArrayList<>(Arrays.asList(100.0, 85.0, 79.5, 94.75)));
        collegeStudent.setStudentGrades(studentGrades);
    }

    @DisplayName("Add Grade Results for Student Grades")
    @Test
    void addGradeResultsForStudentGrades() {
        assertEquals(359.25, studentGrades.addGradeResultsForSingleClass(
                collegeStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("assertNotEquals for Add Grade Results")
    @Test
    void testingNotEqualsForAddGradeResults() {
        assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
                collegeStudent.getStudentGrades().getMathGradeResults()
        ));
    }

    @DisplayName("Is the grade TRULY greater?")
    @Test
    void testIsGradeGreaterMethod() {
        assertTrue(studentGrades.isGradeGreater(90, 75),
                "Andria says, '90 is TRULY greater and 75'");
    }

    @DisplayName("The grade ISN'T greater.")
    @Test
    void assertFalseGradeGreaterMethod() {
        assertFalse(studentGrades.isGradeGreater(70, 95),
                "Andria says, '70 is NOT greater and 95'");
    }

    @DisplayName("Checking for Null student grades")
    @Test
    void testCheckNullMethod() {
        assertNotNull(studentGrades.checkNull(collegeStudent.getStudentGrades().getMathGradeResults()),
                "Andria says, 'This was set in @BeforeEach'");
    }

    @DisplayName("Checking creation - student w/o @BeforeEach")
    @Test
    public void createStudentWithoutGradesInit() {
        CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
        studentTwo.setFirstname("Chad");
        studentTwo.setLastname("Darby");
        studentTwo.setEmailAddress("chad.darby@luv2code_school.com");
        assertNotNull(studentTwo.getFirstname());
        assertNotNull(studentTwo.getLastname());
        assertNotNull(studentTwo.getEmailAddress());
        assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
    }

    @DisplayName("Verify students are prototypes")
    @Test
    public void verifyStudentsArePrototypes() {
        CollegeStudent studentTwo = context.getBean
                ("collegeStudent", CollegeStudent.class);

        assertNotSame(collegeStudent, studentTwo);
    }

    @DisplayName("assertAll - finding Sum & GPA")
    @Test
    public void findGradePointAverage() {
        assertAll("Testing all assertEquals",
                ()-> assertEquals(359.25, studentGrades.addGradeResultsForSingleClass(
                        collegeStudent.getStudentGrades().getMathGradeResults())),
                ()-> assertEquals(89.81, studentGrades.findGradePointAverage(
                        collegeStudent.getStudentGrades().getMathGradeResults()))
        );
    }

}