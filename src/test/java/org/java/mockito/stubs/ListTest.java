package org.java.mockito.stubs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.util.List;

public class ListTest {
    @Test
    void testMockList_When_SizeIsCalled_ShouldReturn10() {
        List<?> list = Mockito.mock(List.class);
        // quando (quando), list.size for chamado, deve retornar 10
        Mockito.when(list.size()).thenReturn(10);
        Assertions.assertEquals(10, list.size());
    }

    @Test
    void testMockList_When_SizeIsCalled_ShouldReturnMultipleValues() {
        List<?> list = Mockito.mock(List.class);
        // quando (quando), list.size for chamado, deve retornar 10
        Mockito.when(list.size())
            .thenReturn(10)
            .thenReturn(20)
            .thenReturn(30);
        Assertions.assertEquals(10, list.size());
        Assertions.assertEquals(20, list.size());
        Assertions.assertEquals(30, list.size());
    }

    @Test
    void testMockList_When_GetIsCalled_ShouldReturnTestValue() {
        List<String> list = Mockito.mock(List.class);
        // “Se pedirem o item da posição 0 da lista, finja que o valor é "Test Value".”
        Mockito.when(list.get(0))
            .thenReturn("Test Value");
        Assertions.assertEquals("Test Value", list.get(0));
        Assertions.assertNull(list.get(2));
    }


    @Test
    void testMockList_When_ThrowsAnException() {
        List<?> list = Mockito.mock(List.class);
        Mockito.when(list.get(ArgumentMatchers.anyInt()))
            .thenThrow(new RuntimeException("Foor Bar"));
        Assertions.assertThrows(
            RuntimeException.class,
            () -> {list.get(ArgumentMatchers.anyInt());},
            () -> "Should have a throw an RuntimeException"
        );
    }
}
