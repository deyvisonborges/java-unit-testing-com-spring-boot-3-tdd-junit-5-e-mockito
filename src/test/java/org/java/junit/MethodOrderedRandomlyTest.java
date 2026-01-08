package org.java.junit;

import org.junit.jupiter.api.*;

// Por padrao , os testes sao renderizados pelo nome
// Mas posso fazer com que rode aleatoriamente
//@TestMethodOrder(MethodOrderer.Random.class) // roda a ordem de forma aleatoria - ordenacao randomica
//@TestMethodOrder(MethodOrderer.MethodName.class) // ordenacao por nome (ja eh padrao, mas estou explicitando)
@Order(3)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // ordenacao por indice
public class MethodOrderedRandomlyTest {

    @Test()
    @Order(2)
    void test_B() {}

    @Test()
    @Order(3)
    void test_C() {}

    @Test()
    @Order(1)
    void test_A() {}

}
