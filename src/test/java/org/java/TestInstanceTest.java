package org.java;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestInstanceTest {
    StringBuilder actualValue = new StringBuilder();

    @AfterEach
    void afterEach() {
        System.out.println("The actual value this " + actualValue);
    }

    @Test()
    @Order(2)
    void test_B() {
        System.out.println("Test B");
        actualValue.append("2");
    }

    @Test()
    @Order(3)
    void test_C() {
        System.out.println("Test C");
        actualValue.append("3");
    }

    @Test()
    @Order(1)
    void test_A() {
        System.out.println("Test A");
        actualValue.append("1");
    }
}
