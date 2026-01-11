package org.java.mockito.stubs;

import org.java.mockito.CourseBusiness;
import org.java.mockito.ICourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseBusinessMockWithBDDTest {
    ICourseService mockService;
    CourseBusiness business;
    List<String> courses;

    @BeforeEach()
    void before() {
//        mockService = mockServicenew CourseServiceStub();
        // Com mockito
        mockService = Mockito.mock(ICourseService.class);
        business = new CourseBusiness(mockService);
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
        // verifique para mim se alguém chamou o metodo deleteCourse dentro do objeto mockService,
        // passando exatamente o texto 'Stub NotRelated' como argumento.
//        Mockito.verify(mockService).deleteCourse("Stub NotRelated");
//        Mockito.verify(mockService, Mockito.times(1))
//            .deleteCourse("Stub NotRelated");
        Mockito.verify(mockService, Mockito.atLeast(1))
                .deleteCourse("Stub NotRelated");
        Mockito.verify(mockService, Mockito.never())
            .deleteCourse("Stub Spring 3");
    }
}
