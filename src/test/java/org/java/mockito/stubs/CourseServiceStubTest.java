package org.java.mockito.stubs;

import org.java.mockito.CourseBusiness;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseServiceStubTest {
    @Test
    void testCourseRelatedToSpring_When_UsingAStub() {
        CourseServiceStub stub = new CourseServiceStub();
        CourseBusiness business = new CourseBusiness(stub);
        var filters = business.retrieveCoursesRelatedToSpring("Mock 1");
        assertEquals(3, filters.size());
    }
}