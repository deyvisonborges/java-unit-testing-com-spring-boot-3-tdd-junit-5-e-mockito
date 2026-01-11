package org.java.mockito.stubs;

import org.java.mockito.CourseBusiness;
import org.java.mockito.ICourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseBusinessMockWithBDDTest {
    ICourseService mockService;
    CourseBusiness business;

    @BeforeEach()
    void before() {
//        mockService = mockServicenew CourseServiceStub();
        // Com mockito
        mockService = Mockito.mock(ICourseService.class);
        business = new CourseBusiness(mockService);
    }
    @Test
    void testCourseRelatedToSpring_When_UsingAStub() {
        List<String> courses = Arrays.asList(
                "Stub Spring 1",
                "Stub Spring 2",
                "Stub Spring 3",
                "Stub Not Sprin.. 3"
        );
        Mockito.when(mockService.retrieveCourse("Spring")).thenReturn(courses);
        var filters = business.retrieveCoursesRelatedToSpring("Spring");
        assertEquals(3, filters.size());
    }
}
