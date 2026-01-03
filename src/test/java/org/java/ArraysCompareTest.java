package org.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ArraysCompareTest {
    @Test()
    void sort() {
        int[] numbers = { 3, 1, 5, 2, 4 };
        int[] expectedOrderedArray = { 1, 2, 3, 4, 5 };
        Arrays.sort(numbers);

        Assertions.assertArrayEquals(numbers, expectedOrderedArray);
    }
}
