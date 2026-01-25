package org.java.mockito.stubs;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.java.mockito.CourseBusiness;
import org.java.mockito.ICourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class) // habilita o Mockito e inicializa as anotações (@Mock, @InjectMocks, etc.)
public class MockitoAdvancedTest {
    // define esse mock em especifico
    // cria um mock de ICourseService
    @Mock
    ICourseService mockService;

    // isso pq o course business, precisa do mock do mockSevrcei, mas eu removi nas linhsa abaixo
    // cria a instância e injeta os mocks compatíveis (priorizando o construtor)
    @InjectMocks
    CourseBusiness business;
    List<String> courses;
    @Captor
    ArgumentCaptor<String> captor;

    @BeforeEach()
    void before() {
//        O @Mock e o @ExtendWith subsituem esse
//        mockService = Mockito.mock(ICourseService.class);
//        business = new CourseBusiness(mockService);
        courses = Arrays.asList(
                "Stub Spring 1",
                "Stub Spring 2",
                "Stub Spring 3",
                "Stub NotRelated"
        );
    }
    @Test
    void testCourseRelatedToSpring_When_UsingAStub() {
        Mockito.when(mockService.retrieveCourse("Spring")).thenReturn(courses);
        var filters = business.retrieveCoursesRelatedToSpring("Spring");
        assertEquals(3, filters.size());
    }

    @Test
    @DisplayName("Delete Courses not related to Spring Use Mockito Should Call Method")
    void test_AXSXS() {
        BDDMockito.given(mockService.retrieveCourse("Spring")).willReturn(courses);
        business.deleteCoursesNotRelatedToSpring("Spring");
        Mockito.verify(mockService, Mockito.atLeastOnce())
                .deleteCourse("Stub NotRelated");
        Mockito.verify(mockService, Mockito.never())
                .deleteCourse("Stub Spring 3");
    }

    @Test
    @DisplayName("Using BDD Mockito then, should never")
    void thenShouldNever() {
        BDDMockito.given(mockService.retrieveCourse("Spring")).willReturn(courses);
        business.deleteCoursesNotRelatedToSpring("Spring");
        BDDMockito.then(mockService)
                .should()
                .deleteCourse("Stub NotRelated");
        BDDMockito.then(mockService)
                .should(BDDMockito.never())
                .deleteCourse("Stub Spring 3");
    }

    @Test
    @DisplayName("Using BDD Mockito then, should never")
    void capturingArguments() {
        BDDMockito.given(mockService.retrieveCourse("Spring")).willReturn(courses);
//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        var arg = "Stub NotRelated";
        business.deleteCoursesNotRelatedToSpring("Spring");
        BDDMockito.then(mockService)
                .should(BDDMockito.times(1))
                .deleteCourse(captor.capture());
        MatcherAssert.assertThat(captor.getValue(), Matchers.is(arg));
    }
}
