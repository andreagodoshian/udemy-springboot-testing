package org.example;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Rigorous Test for simple App :-)
 */

@DisplayNameGeneration(DisplayNameGenerator.IndicativeSentences.class)
// @TestMethodOrder(MethodOrderer.MethodName.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoUtilsTest {

    DemoUtils demoUtils;

    @BeforeAll
    static void runBeforeAll() {
        System.out.println("Good luck!");
    }

    @AfterAll
    static void runAfterAll() {
        System.out.println("Great success!");
    }

    @BeforeEach
    void runBeforeEach() {
        demoUtils = new DemoUtils(); // don't re-declare it!
    }

    @AfterEach
    void runAfterEach() {}

    ////////////////////////////////////////

    @Test
    @Order(-1)
    // @DisplayName("Test the Addition Method")
    void testAddMethod() {
        assertEquals(6, demoUtils.add(2, 4),
                "Andria says, '2+4 equals 6'");
        assertNotEquals(6, demoUtils.add(1, 9),
                "Andria says, '1+9 not-equals 6'");
    }

    @Test
    @Order(0)
    // @DisplayName("Test the Check-Null Method")
    void testCheckNullMethod() {
        String nullString = null;
        String notNullString = "Git Money";

        assertNull(demoUtils.checkNull(nullString),
                "Andria says, 'nullString' should be null");
        assertNotNull(demoUtils.checkNull(notNullString),
                "Andria says, 'notNullString' should be not-null");
    }

    @Test
    // @DisplayName("Testing Junit's Same/Not-same")
    void testJunitSameNotSame() {
        String answer = "Yaroslavl State Technical University";

        assertSame(demoUtils.getAcademy(), demoUtils.getAcademyDuplicate(),
                "Andria says, 'I assert they are the same!'");
        assertSame(demoUtils.getAcademy(), answer,
                "Andria says, 'I assert they are the same!'");
        assertNotSame(answer.toLowerCase(), demoUtils.getAcademy(),
                "Andria says, 'I assert they are NOT be the same!'");
    }

    @Test
    // @DisplayName("Testing the isGreater Method")
    void testJunitTrueFalse() {
        int largerNumber = 10;
        int smallerNumber = 5;

        assertTrue(demoUtils.isGreater(largerNumber, smallerNumber),
                "Andria says, 'True: 10>5'");
        assertFalse(demoUtils.isGreater(smallerNumber, largerNumber),
                "Andria says, 'False: 5 !> 10'");
    }

    @Test
    void testJunitArrayEquals() {
        String[] localArray = {"A", "B", "C"};

        assertArrayEquals(localArray, demoUtils.getFirstThreeLetters(),
                "Andria says, 'these arrays should be equal!'");
    }

    @Test
    void testJunitIterableEquals() {
        List<String> localList = List.of
                ("Yaroslavl", "State", "Technical", "University");

        assertIterableEquals(localList, demoUtils.getAcademyAsList(),
                "Andria says, 'These iterables are equal!'");
    }

    @Test
    void testJunitLinesMatch() {
        List<String> localList = List.of
                ("Yaroslavl", "State", "Technical", "University");

        assertLinesMatch(localList, demoUtils.getAcademyAsList(),
                "Andria says, 'These lines are equal!'");
    }

    @Test
    void testThrowExceptionMethod() {
        assertThrows(Exception.class, () ->
        {demoUtils.throwExceptionMethod(-1);},
                "Andria says, 'Needs to be greater than zero!" +
                        " Are you a sadist?'");

        assertDoesNotThrow(() ->
                {demoUtils.throwExceptionMethod(99);},
                "Andria says, 'Should be greater than zero!" +
                        " Don't be a sadist!'");
    }

    @Test
    void testSetTimeoutMethod() {

        assertTimeoutPreemptively(Duration.ofSeconds(3),
                () -> {
                    demoUtils.checkTimeout();},
                "Andria says, 'Don't oversleep!" +
                        " You get three seconds MAXIMUM," +
                        " because life fucking sucks.'");

    }

}