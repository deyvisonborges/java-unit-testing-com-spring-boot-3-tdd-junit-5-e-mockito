package org.java;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Test Math Operations in SimpleMath Class")
class SimpleMathTest {

    /**
     * [System Under Test]
     * [Condition or State Change]
     * [Expected Result]
     */
    @Test
    @DisplayName("Test if 6.2 + 2 = 8.2")
    void testSum_when_SixDotTwoIsAddedByTwo_ShouldReturnEightDotTwo() {
        SimpleMath math = new SimpleMath();
        Double actual = math.sum(6.2D, 2D);
        double expected = 8.2D;
        assertEquals(expected, actual, () -> "The testSum() did not produce expect result");
        assertNotEquals(9.2, actual);
        assertNotNull(actual);
    }

    @Test
    @DisplayName("Subtract values")
    void subtraction() {
        SimpleMath math  = new SimpleMath();
        assertEquals(1.0D, math.subtraction(2.0D, 1.0D), () -> "Fail to subtract values");
    }

    @Test
    void multiplication() {
    }

    @Test()
    void testDivision_When_FirstNumberIsDividedByZero_ShouldThrowArithmeticException() {
        fail();
    }
}