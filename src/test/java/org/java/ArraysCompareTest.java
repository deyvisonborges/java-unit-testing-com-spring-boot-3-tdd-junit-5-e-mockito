package org.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class ArraysCompareTest {
    @Test()
    void sort() {
        int[] numbers = { 3, 1, 5, 2, 4 };
        int[] expectedOrderedArray = { 1, 2, 3, 4, 5 };
        Arrays.sort(numbers);

        Assertions.assertArrayEquals(numbers, expectedOrderedArray);
    }

    @Test()
//    @Timeout(1)
    @Timeout(value = 15, unit = TimeUnit.MILLISECONDS)
    void testSortPerformance() {
        int[] numbers = { 3, 1, 5, 2, 4 };
        for (int i = 0; i < 1000000000; i++) {
            numbers[0] = i;
            Arrays.sort(numbers);
        }
    }
}
