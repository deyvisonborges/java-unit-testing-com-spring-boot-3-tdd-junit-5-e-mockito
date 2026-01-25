package org.java.mockito.stubs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
// O mockito.spy geralmente eh mais utilizado em projetos legados
public class SpyTest {
    @Test
    void testV1() {
        List<String> mockArrayList = Mockito.spy(new ArrayList<>());
        Assertions.assertEquals(0, mockArrayList.size());
        BDDMockito.when(mockArrayList.size()).thenReturn(5);
        mockArrayList.add("Foo-Bar");
        Assertions.assertEquals(5, mockArrayList.size());
    }

// https://www.browserstack.com/guide/mockito-spy-vs-mockito-mock
    @Test
    void testV2() {
        List<String> spyArrayList = Mockito.spy(new ArrayList<>());
        Assertions.assertEquals(0, spyArrayList.size());

        spyArrayList.add("Foo-Bar");
        Assertions.assertEquals(1, spyArrayList.size());

        spyArrayList.remove("Foo-Bar");
        Assertions.assertEquals(0, spyArrayList.size());
    }

    @Test
    void testV3() {
        List<String> spyArrayList = Mockito.spy(new ArrayList<>());
        Assertions.assertEquals(0, spyArrayList.size());
        BDDMockito.when(spyArrayList.size()).thenReturn(5);
        Assertions.assertEquals(5, spyArrayList.size());
    }

    @Test
    void testV4() {
        List<String> spyArrayList = Mockito.spy(new ArrayList<>());
        spyArrayList.add("Foo-Bar");
        Mockito.verify(spyArrayList).add("Foo-Bar");
        Mockito.verify(spyArrayList, Mockito.never()).remove("Foo-Bar");
        Mockito.verify(spyArrayList, Mockito.never()).remove(ArgumentMatchers.anyString());
        Mockito.verify(spyArrayList, Mockito.never()).clear();
    }
}
