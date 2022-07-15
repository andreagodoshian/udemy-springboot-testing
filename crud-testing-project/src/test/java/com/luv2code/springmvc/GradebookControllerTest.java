package com.luv2code.springmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springmvc.models.*;
import com.luv2code.springmvc.repository.*;
import com.luv2code.springmvc.service.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GradebookControllerTest {

    private static MockHttpServletRequest request;

    private static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

    ///////////////////////////

    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    StudentAndGradeService studentAndGradeServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbc;

    ///////////////////////////

    @Autowired
    private CollegeStudent student;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private MathGradesDao mathGradeDao;

    @Autowired
    private ScienceGradesDao scienceGradeDao;

    @Autowired
    private HistoryGradesDao historyGradeDao;

    @Autowired
    private StudentAndGradeService studentService;

    ///////////////////////////

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    ///////////////////////////

    @BeforeAll
    public static void testBeforeAll() {
        request = new MockHttpServletRequest();

        request.setParameter("firstname", "Daria");
        request.setParameter("lastname", "Morgendorffer");
        request.setParameter("emailAddress", "daria@sicksadworld.com");
    }

    ///////////////////////////

    @BeforeEach
    void testBeforeEach() {
        jdbc.execute(sqlAddStudent);
        jdbc.execute(sqlAddMathGrade);
        jdbc.execute(sqlAddScienceGrade);
        jdbc.execute(sqlAddHistoryGrade);
    }

    @AfterEach
    void testAfterEach() {
        jdbc.execute(sqlDeleteStudent);
        jdbc.execute(sqlDeleteMathGrade);
        jdbc.execute(sqlDeleteScienceGrade);
        jdbc.execute(sqlDeleteHistoryGrade);
    }

    ///////////////////////////

    @Test
    void testGetAllStudentsHttp() throws Exception {
        student.setFirstname("Jane");
        student.setLastname("Lane");
        student.setEmailAddress("jane@sicksadworld.com");
        entityManager.persist(student);
        entityManager.flush();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetStudentInfoHttp() throws Exception {
        Optional<CollegeStudent> student = studentDao.findById(1);
        assertTrue(student.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstname", is("Sharon")))
                .andExpect(jsonPath("$.lastname", is("Marsh")))
                .andExpect(jsonPath("$.emailAddress", is("sharon@southparkstudios.com")));
    }

    @Test
    void testErrorGetStudentInfoHttp() throws Exception {
        Optional<CollegeStudent> student = studentDao.findById(0);
        assertFalse(student.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", is("Student or Grade was not found")));
    }

    ///////////////////////////

    @Test
    void testPostStudentHttp() throws Exception {
        student.setFirstname("Trent");
        student.setLastname("Lane");
        student.setEmailAddress("trent@mystiksprial.com");

        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        // ~~~~~~~~~~~~~~~~~ //
        CollegeStudent verifyStudent = studentDao.findByEmailAddress("trent@mystiksprial.com");
        assertNotNull(verifyStudent, "Didn't we add Trent?");
    }

    ///////////////////////////

    @Test
    void testDeleteStudentHttp() throws Exception {
        assertTrue(studentDao.findById(1).isPresent());
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));

        assertFalse(studentDao.findById(1).isPresent());
    }

    @Test
    void testErrorDeleteStudentHttp() throws Exception {
        assertFalse(studentDao.findById(0).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message",
                        is("Student or Grade was not found")));
    }

    ///////////////////////////



}