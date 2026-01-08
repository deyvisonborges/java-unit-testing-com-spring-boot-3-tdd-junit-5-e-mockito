package org.java.junit;

import org.java.SimpleMath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math Operations in SimpleMath Class")
class SimpleMathTestParameterized {
    SimpleMath math;

    @BeforeEach()
    void before() {
        this.math = new SimpleMath();
        System.out.println("Before each");
    }

    @ParameterizedTest
    @MethodSource("testDivideInputParameters")
    void testDivide(double f, double s, double expected) {
        Double actual = this.math.divide(f, s);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> testDivideInputParameters() {
        return Stream.of(
            Arguments.of(6.2D, 2D, 3.1D),
            Arguments.of(8D, 2D, 4D)
        );
    }
}
