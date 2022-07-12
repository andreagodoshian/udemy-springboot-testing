package org.example;

import org.example.dao.ApplicationDao;
import org.example.models.CollegeStudent;
import org.example.models.StudentGrades;
import org.example.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent collegeStudent;

    @Autowired
    StudentGrades studentGrades;

    @Mock
    private ApplicationDao applicationDao;

    @InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        collegeStudent.setFirstname("Jane");
        collegeStudent.setLastname("Lane");
        collegeStudent.setEmailAddress("jane@sicksadworld.com");
        collegeStudent.setStudentGrades(studentGrades);
    }

    @DisplayName("When 'xyz' - assert 'PDQ' && verify")
    @Test
    void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass
                (studentGrades.getMathGradeResults())).thenReturn(100.00);

        assertEquals(100, applicationService
                .addGradeResultsForSingleClass(collegeStudent.getStudentGrades()
                        .getMathGradeResults()));

        verify(applicationDao).addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults());

        verify(applicationDao, times(1)).addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults());
    }
}