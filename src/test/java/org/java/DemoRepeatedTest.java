package org.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DemoRepeatedTest {
    SimpleMath math;
    @BeforeEach()
    void before() {
        this.math = new SimpleMath();
        System.out.println("Before each");
    }

//    @RepeatedTest(3)
    @RepeatedTest(value = 3, name = "{displayName}. Repetition " + "{currentRepetition} of {totalRepetitions}!")
    void testDivision_When_FirstNumberIsDividedByZero_ShouldThrowArithmeticException(
        RepetitionInfo info
    ) {
        System.out.println("Repetition info " + info.getCurrentRepetition());
        var expectedMessage = "Impossible divide by zero";
        var actual = assertThrows(ArithmeticException.class, () -> {
            math.divide(6.2D, 0D);
        }, () -> "Actual message");
        assertEquals(expectedMessage, actual.getMessage(), () -> "Unexpected exception message");
    }
}
