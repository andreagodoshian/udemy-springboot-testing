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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockBeanAnnotationTest {

    // basically the same, just changing these two annotations
    // @Autowired is arguably more versatile

    @MockBean
    private ApplicationDao applicationDao;

    @Autowired
    private ApplicationService applicationService;

    ///////////////////////////////////////////////////////

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent collegeStudent;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void beforeEach() {
        collegeStudent.setFirstname("Jane");
        collegeStudent.setLastname("Lane");
        collegeStudent.setEmailAddress("jane@sicksadworld.com");
        collegeStudent.setStudentGrades(studentGrades);
    }

    @DisplayName("Add Grades (When - Assert - Verify)")
    @Test
    void assertEqualsTestAddGrades() {
        when(applicationDao.addGradeResultsForSingleClass
                (studentGrades.getMathGradeResults())).thenReturn(100.00);

        assertEquals(100, applicationService
                .addGradeResultsForSingleClass(collegeStudent.getStudentGrades()
                        .getMathGradeResults()));

        verify(applicationDao).addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults());

        // this is fun for seeing how many times it was called
        verify(applicationDao, times(1)).addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults());
    }

    @DisplayName("Find GPA (When - Assert)")
    @Test
    void assertEqualsFindGpa() {
        when (applicationDao.findGradePointAverage(
                studentGrades.getMathGradeResults())).thenReturn(88.31);

        assertEquals(88.31,
                applicationService.findGradePointAverage(
                        collegeStudent.getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("checkNull Method (When - Assert)")
    @Test
    void testAssertNotNull() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenReturn(true);

        assertNotNull(applicationService.checkNull(collegeStudent.getStudentGrades()
                .getMathGradeResults()), "Andria says, 'Not null - @BeforeEach'");

    }

    @DisplayName("will Throw Exception? (doThrow - Assert)")
    @Test
    void testThrowRuntimeError() {
        CollegeStudent nullStudent =
                (CollegeStudent) context.getBean("collegeStudent");

        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        // make sure it's not looping ;)
        verify(applicationDao, times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multi-Calls? (assertThrows THEN assertEquals)")
    @Test
    void testConsecutiveCalls() {
        CollegeStudent nullStudent =
                (CollegeStudent) context.getBean("collegeStudent");

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Don't throw exception a second time!");

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        assertEquals("Don't throw exception a second time!",
                applicationService.checkNull(nullStudent));

        verify(applicationDao, times(2)).checkNull(nullStudent);
    }
}