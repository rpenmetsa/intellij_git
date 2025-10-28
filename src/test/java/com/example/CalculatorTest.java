package com.example;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import java.time.Duration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

/**
 * Comprehensive JUnit 5 test class demonstrating all major annotations
 */
@DisplayName("Calculator Test Suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CalculatorTest {

    private Calculator calculator;
    private static int testCounter;

    // Runs once before all tests in this class
    @BeforeAll
    static void initAll() {
        System.out.println("=== Starting Calculator Test Suite ===");
        testCounter = 0;
    }

    // Runs before each test method
    @BeforeEach
    void init() {
        calculator = new Calculator();
        testCounter++;
        System.out.println("Test #" + testCounter + " starting...");
    }

    // Basic test annotation
    @Test
    @DisplayName("Test addition of two positive numbers")
    @Order(1)
    void testAddition() {
        assertEquals(5, calculator.add(2, 3), "2 + 3 should equal 5");
    }

    @Test
    @DisplayName("Test subtraction")
    @Order(2)
    void testSubtraction() {
        assertEquals(1, calculator.subtract(3, 2));
    }

    @Test
    @DisplayName("Test multiplication")
    @Order(3)
    void testMultiplication() {
        int result = calculator.multiply(4, 5);
        assertEquals(20, result, "4 * 5 should equal 20");
    }

    @Test
    @DisplayName("Test division")
    @Order(4)
    void testDivision() {
        assertEquals(2.5, calculator.divide(5, 2), 0.001);
    }

    // Test for expected exceptions
    @Test
    @DisplayName("Test division by zero throws exception")
    @Order(5)
    void testDivisionByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
        assertEquals("Cannot divide by zero", exception.getMessage());
    }

    // Parameterized test with multiple values
    @ParameterizedTest
    @DisplayName("Test even numbers")
    @ValueSource(ints = {2, 4, 6, 8, 10})
    @Order(6)
    void testIsEven(int number) {
        assertTrue(calculator.isEven(number), number + " should be even");
    }

    // Parameterized test with CSV source
    @ParameterizedTest
    @DisplayName("Test square calculations")
    @CsvSource({
            "2, 4",
            "3, 9",
            "4, 16",
            "5, 25"
    })
    @Order(7)
    void testSquare(int input, int expected) {
        assertEquals(expected, calculator.square(input));
    }

    // Nested test class for grouping related tests
    @Nested
    @DisplayName("Negative number tests")
    class NegativeNumberTests {

        @Test
        @DisplayName("Test addition with negative numbers")
        void testAddNegative() {
            assertEquals(-1, calculator.add(-3, 2));
        }

        @Test
        @DisplayName("Test multiplication with negative numbers")
        void testMultiplyNegative() {
            assertEquals(-12, calculator.multiply(-3, 4));
        }
    }

    // Disabled test - won't run
    @Test
    @Disabled("Not implemented yet")
    @DisplayName("Test complex calculations")
    void testComplexCalculation() {
        // This test is disabled and won't run
        fail("This test should not run");
    }

    // Test with timeout
    @Test
    @DisplayName("Test performance")
    @Order(8)
    void testPerformance() {
        assertTimeout(Duration.ofSeconds(1), () -> {
            int result = 0;
            for (int i = 0; i < 1000; i++) {
                result = calculator.add(result, 1);
            }
            assertEquals(1000, result);
        });
    }

    // Conditional test - only runs on specific OS
    @Test
    @DisplayName("Test that runs on Windows only")
    @EnabledOnOs(OS.WINDOWS)
    void testOnWindows() {
        assertTrue(true, "This test runs only on Windows");
    }

    // Test with assumptions
    @Test
    @DisplayName("Test with assumptions")
    void testWithAssumptions() {
        assumeTrue(System.getProperty("os.name").contains("Linux") ||
                System.getProperty("os.name").contains("Windows") ||
                System.getProperty("os.name").contains("Mac"));

        assertEquals(10, calculator.add(5, 5));
    }

    // Multiple assertions in one test
    @Test
    @DisplayName("Test multiple assertions together")
    @Order(9)
    void testMultipleAssertions() {
        assertAll("calculator operations",
                () -> assertEquals(5, calculator.add(2, 3)),
                () -> assertEquals(1, calculator.subtract(3, 2)),
                () -> assertEquals(20, calculator.multiply(4, 5)),
                () -> assertTrue(calculator.isEven(4))
        );
    }

    // Runs after each test method
    @AfterEach
    void tearDown() {
        System.out.println("Test #" + testCounter + " completed.");
        calculator = null;
    }

    // Runs once after all tests in this class
    @AfterAll
    static void tearDownAll() {
        System.out.println("=== Calculator Test Suite Completed ===");
        System.out.println("Total tests executed: " + testCounter);
    }
}