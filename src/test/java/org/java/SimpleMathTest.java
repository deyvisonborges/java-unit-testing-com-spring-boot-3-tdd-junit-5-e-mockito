package org.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

@DisplayName("Test Math Operations in SimpleMath Class")
class SimpleMathTest {
    SimpleMath math;

    @BeforeAll
    static void setup() {
        System.out.println("===> Initialize Before All");
    }

    @AfterAll()
    static void cleanup() {
        System.out.println("<=== Finish tests process");
    }

    @BeforeEach()
    void before() {
        this.math = new SimpleMath();
        System.out.println("Before each");
    }

    @AfterEach()
    void after() {
        System.out.println("After each");
    }

    /**
     * [System Under Test]
     * [Condition or State Change]
     * [Expected Result]
     */
    @Test
    @DisplayName("Test if 6.2 + 2 = 8.2")
    void testSum_when_SixDotTwoIsAddedByTwo_ShouldReturnEightDotTwo() {
        System.out.println("Sum method");
        Double actual = math.sum(6.2D, 2D);
        double expected = 8.2D;
        assertEquals(expected, actual, () -> "The testSum() did not produce expect result");
        assertNotEquals(9.2, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("Subtract values")
    void subtraction() {
        System.out.println("Sum method");
        assertEquals(1.0D, math.subtraction(2.0D, 1.0D), () -> "Fail to subtract values");
    }

    @Test()
    void multiplication() {
        System.out.println("Sum method");
    }

    @Test()
    @Disabled("Test Disabled")
    void testDivision_When_FirstNumberIsDividedByZero_ShouldThrowArithmeticException() {
        fail();
    }
}