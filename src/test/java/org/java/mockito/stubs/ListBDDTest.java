package org.java.mockito.stubs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.BDDMockito;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.List;

public class ListBDDTest {
    @Test
    void testMockList_When_SizeIsCalled_ShouldReturn10() {
        List<?> list = Mockito.mock(List.class);
        // quando (quando), list.size for chamado, deve retornar 10
        BDDMockito.given(list.size()).willReturn(10);
        MatcherAssert.assertThat(list.size(), Matchers.is(10));
    }

    @Test
    void testMockList_When_SizeIsCalled_ShouldReturnMultipleValues() {
        List<?> list = BDDMockito.mock(List.class);
        // quando (quando), list.size for chamado, deve retornar 10
        BDDMockito.given(list.size())
                .willReturn(10)
                .willReturn(20)
                .willReturn(30);
        MatcherAssert.assertThat(list.size(), Matchers.is(10));
        MatcherAssert.assertThat(list.size(), Matchers.is(20));
        MatcherAssert.assertThat(list.size(), Matchers.is(30));
    }

    @Test
    void testMockList_When_GetIsCalled_ShouldReturnTestValue() {
        List<String> list = BDDMockito.mock(List.class);
        // “Se pedirem o item da posição 0 da lista, finja que o valor é "Test Value".”
        BDDMockito.given(list.get(0))
                .willReturn("Test Value");
        MatcherAssert.assertThat(list.get(0), Matchers.is("Test Value"));
        Assertions.assertNull(list.get(2));
    }


    @Test
    void testMockList_When_ThrowsAnException() {
        List<?> list = BDDMockito.mock(List.class);
        BDDMockito.given(list.get(ArgumentMatchers.anyInt()))
                .willThrow(new RuntimeException("Foor Bar"));
        Assertions.assertThrows(
                RuntimeException.class,
                () -> {list.get(ArgumentMatchers.anyInt());},
                () -> "Should have a throw an RuntimeException"
        );
    }
}
