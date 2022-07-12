package org.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Rigorous Test for simple App :-)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FizzBuzzTest {

    @DisplayName("Divisible By Three ONLY")
    @Order(1)
    @Test
    void testDivisibleBy3Only() {
        String expected = "fizz";

        assertEquals(expected, FizzBuzz.compute(3),
                "Andria says, 'Should be fizz since only by 3.'");
    }

    @DisplayName("Divisible By Five ONLY")
    @Order(2)
    @Test
    void testDivisibleBy5Only() {
        String expected = "buzz";

        assertEquals(expected, FizzBuzz.compute(5),
                "Andria says, 'Should be buzz since only by 5.'");
    }

    @DisplayName("Divisible By 3 AND 5")
    @Order(3)
    @Test
    void testDivisibleBy3And5() {
        String expected = "fizzbuzz";

        assertEquals(expected, FizzBuzz.compute(15),
                "Andria says, 'Should be fizzbuzz since 3 and 5.'");
    }

    @DisplayName("Not Divisible By 3 and/or 5")
    @Order(4)
    @Test
    void testOtherNumbers() {
        String expected = "1";

        assertEquals(expected, FizzBuzz.compute(1),
                "Andria says, 'Should just be the number, since 3 nor 5.'");
    }

    @DisplayName("Testing Small FizzBuzz Csv")
    @Order(5)
    @ParameterizedTest(name = "input={0}, result={1}") // INDEXES!!
    @CsvFileSource(resources = "/small-fizzbuzz-test-data.csv")
    void testSmallDataFile(int input, String result) {

        assertEquals(result, FizzBuzz.compute(input));
    }

    @DisplayName("Testing Medium FizzBuzz Csv")
    @Order(6)
    @ParameterizedTest(name = "input={0}, result={1}") // INDEXES!!
    @CsvFileSource(resources = "/medium-test-data.csv")
    void testMediumDataFile(int input, String result) {

        assertEquals(result.toLowerCase(), FizzBuzz.compute(input));
    }

    @DisplayName("Testing Large FizzBuzz Csv")
    @Order(7)
    @ParameterizedTest(name = "input={0}, result={1}") // INDEXES!!
    @CsvFileSource(resources = "/large-test-data.csv")
    void testLargeDataFile(int input, String result) {

        assertEquals(result.toLowerCase(), FizzBuzz.compute(input));
    }

}