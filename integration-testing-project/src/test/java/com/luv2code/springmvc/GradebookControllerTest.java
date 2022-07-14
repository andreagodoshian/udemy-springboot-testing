package com.luv2code.springmvc;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class GradebookControllerTest {

    @Autowired
    private StudentDao studentDao;

    ////////////////////////////////////////////

    // has to be static, because being used in @BeforeAll
    private static MockHttpServletRequest request;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentAndGradeService studentCreateServiceMock;

    ////////////////////////////////////////

    @BeforeAll // has to be public static, since @BeforeAll
    public static void beforeAll() {
        request = new MockHttpServletRequest();

        request.setParameter("firstname", "Sharon");
        request.setParameter("lastname", "Marsh");
        request.setParameter("emailAddress", "sharon@southparkstudios.com");
    }

    ////////////////////////////////////////

    @BeforeEach
    void testBeforeEach() {
        jdbc.execute("INSERT INTO student(id, firstname, lastname, email_address) " +
                "VALUES (1, 'Daria', 'Morgendorffer', 'daria@sicksadworld.com')");
    }

    @AfterEach
    void testAfterEach() {
        jdbc.execute("DELETE FROM student");
    }

    ////////////////////////////////////////

    @Test
    void testGetStudentsHttpRequest() throws Exception {
        CollegeStudent studentOne = new GradebookCollegeStudent(
                "Jane", "Lane", "jane@sicksadworld.com");

        CollegeStudent studentTwo = new GradebookCollegeStudent(
                "Trent", "Lane", "trent@mystikspiral.com");

        List<CollegeStudent> collegeStudentList =
                new ArrayList<>(Arrays.asList(studentOne, studentTwo));

        when(studentCreateServiceMock.getGradebook())
                .thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList,
                studentCreateServiceMock.getGradebook());

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

        MvcResult mvcResult = mockMvc.perform((MockMvcRequestBuilders.get("/")))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");
    }

    @Test
    void testCreateStudentHttpRequest() throws Exception {

        CollegeStudent studentOne = new GradebookCollegeStudent(
                "Timmy", "Nook", "timmy@nookinc.com");

        CollegeStudent studentTwo = new GradebookCollegeStudent(
                "Tommy", "Nook", "tommy@nookinc.com");

        List<CollegeStudent> collegeStudentList =
                new ArrayList<>(Arrays.asList(studentOne, studentTwo));

        when(studentCreateServiceMock.getGradebook())
                .thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList,
                studentCreateServiceMock.getGradebook());

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ //

        MvcResult mvcResult = this.mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("firstname", request.getParameterValues("firstname"))
                .param("lastname", request.getParameterValues("lastname"))
                .param("emailAddress", request.getParameterValues("emailAddress"))
        ).andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");

        CollegeStudent verifyStudent = studentDao
                .findByEmailAddress("sharon@southparkstudios.com");

        assertNotNull(verifyStudent, "Andria doesn't want to say anything.");
    }


}