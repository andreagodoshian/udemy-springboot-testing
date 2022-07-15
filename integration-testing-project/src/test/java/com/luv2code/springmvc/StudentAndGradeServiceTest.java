package com.luv2code.springmvc;

import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.*;
import com.luv2code.springmvc.service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    ////////////////////////////////////////

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    ////////////////////////////////////////

    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    ////////////////////////////////////////

    @BeforeEach
    void setupDatabase() {
        jdbc.execute("INSERT INTO student(id, firstname, lastname, email_address) " +
                "VALUES (1, 'Daria', 'Morgendorffer', 'daria@sicksadworld.com')");

        jdbc.execute("INSERT INTO math_grade(id, student_id, grade) " +
                "VALUES (1, 1, 100.00)");

        jdbc.execute("INSERT INTO science_grade(id, student_id, grade) " +
                "VALUES (1, 1, 100.00)");

        jdbc.execute("INSERT INTO history_grade(id, student_id, grade) " +
                "VALUES (1, 1, 100.00)");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbc.execute("DELETE FROM student");
        jdbc.execute("DELETE FROM math_grade");
        jdbc.execute("DELETE FROM science_grade");
        jdbc.execute("DELETE FROM history_grade");
    }

    ////////////////////////////////////////

    @Test
    void createStudentService() {

        studentService.createStudent("Jane", "Lane",
                "jane@sicksadworld.com");

         CollegeStudent student = studentDao.
                 findByEmailAddress("jane@sicksadworld.com");

         assertEquals("jane@sicksadworld.com",
                 student.getEmailAddress(), "Andria says, 'Find by email!'");
    }

    @Test
    void isStudentNullCheck() {

        assertTrue(studentService.checkIfStudentIsNull(1));

        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    void testDeleteStudentService() {

        Optional<CollegeStudent> student = studentDao.findById(1);

        Optional<MathGrade> delMathGrade = mathGradesDao.findById(1);
        Optional<ScienceGrade> delScienceGrade = scienceGradesDao.findById(1);
        Optional<HistoryGrade> delHistoryGrade = historyGradesDao.findById(1);

        /////////////////////////////////////

        assertTrue(student.isPresent(),
                "Andria says, 'Created during @BeforeEach!'");

        assertTrue(delMathGrade.isPresent());
        assertTrue(delScienceGrade.isPresent());
        assertTrue(delHistoryGrade.isPresent());

        /////////////////////////////////////
        studentService.deleteStudent(1);
        /////////////////////////////////////

        student = studentDao.findById(1);

        delMathGrade = mathGradesDao.findById(1);
        delScienceGrade = scienceGradesDao.findById(1);
        delHistoryGrade = historyGradesDao.findById(1);

        /////////////////////////////////////

        assertFalse(student.isPresent(),
                "Andria says, 'Should be deleted && null'");

        assertFalse(delMathGrade.isPresent());
        assertFalse(delScienceGrade.isPresent());
        assertFalse(delHistoryGrade.isPresent());
    }

    @Sql("/insertData.sql")
    @Test
    void testGetGradebookService() {
        Iterable<CollegeStudent> iterableCollegeStudents =
                studentService.getGradebook();

        List<CollegeStudent> collegeStudents = new ArrayList<>();
        for (CollegeStudent x:iterableCollegeStudents) {
            collegeStudents.add(x);
        }

        assertEquals(6, collegeStudents.size());
    }

    @Test
    void testCreateMathGrade() {
        // create the grade -> get all grades by ID -> verify

        assertTrue(studentService.createGrade(80.50, 1, "math"));

        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);

        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2,
                "Can add message, if you're not burnt out...");
    }

    @Test
    void testCreateScienceGrade() {
        // create the grade -> get all grades by ID -> verify

        assertTrue(studentService.createGrade(80.50, 1, "science"));

        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);

        assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2,
                "Can add message, if you're not burnt out...");
    }

    @Test
    void testCreateHistoryGrade() {
        // create the grade -> get all grades by ID -> verify

        assertTrue(studentService.createGrade(80.50, 1, "history"));

        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);

        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2,
                "Can add message, if you're not burnt out...");
    }

    @Test
    void testErrorCreatingGrade() {
        assertFalse(studentService.createGrade(180, 1, "history"));
        assertFalse(studentService.createGrade(-80, 1, "history"));

        assertFalse(studentService.createGrade(80.50, 2, "history"));

        assertFalse(studentService.createGrade(80.50, 1, "literature"));
    }

    @Test
    void testDeleteGradeService() {
        assertEquals(1, studentService.deleteGrade(1, "math"),
                "Should return id after deletion");

        assertEquals(1, studentService.deleteGrade(1, "science"),
                "Should return id after deletion");

        assertEquals(1, studentService.deleteGrade(1, "history"),
                "Should return id after deletion");
    }

    @Test
    void testErrorDeletingGrade() {
        assertEquals(0, studentService.deleteGrade(0, "math"),
                "Should return id after deletion");

        assertEquals(0, studentService.deleteGrade(1, "literature"),
                "Should return id after deletion");
    }

    @Test
    void testStudentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent =
                studentService.studentInformation(1);

        assertNotNull(gradebookCollegeStudent);
        assertEquals(1, gradebookCollegeStudent.getId());
        assertEquals("Daria", gradebookCollegeStudent.getFirstname());
        assertEquals("Morgendorffer", gradebookCollegeStudent.getLastname());
        assertEquals("daria@sicksadworld.com", gradebookCollegeStudent.getEmailAddress());

        assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);
        assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
    }

    @Test
    void testErrorStudentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent =
                studentService.studentInformation(0);

        assertNull(gradebookCollegeStudent);
    }

}