package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application.properties")
@SpringBootTest
public class StudentAndGradeServiceTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;

    ////////////////////////////////////////

    @BeforeEach
    void setupDatabase() {
        jdbc.execute("INSERT INTO student(id, firstname, lastname, email_address) " +
                "VALUES (1, 'Daria', 'Morgendorffer', 'daria@sicksadworld.com')");
    }

    @AfterEach
    void setupAfterTransaction() {
        jdbc.execute("DELETE FROM student");
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
        assertTrue(student.isPresent(),
                "Andria says, 'Created during @BeforeEach!'");

        studentService.deleteStudent(1);

        student = studentDao.findById(1);
        assertFalse(student.isPresent(),
                "Andria says, 'Should be deleted && null'");
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

}