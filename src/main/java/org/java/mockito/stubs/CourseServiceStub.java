package org.java.mockito.stubs;

import org.java.mockito.ICourseService;

import java.util.Arrays;
import java.util.List;

public class CourseServiceStub implements ICourseService {
    @Override
    public List<String> retrieveCourse(String student) {
        return Arrays.asList(
            "Stub Spring 1",
            "Stub Spring 2",
            "Stub Spring 3",
            "Stub Not Sprin.. 3"
        );
    }
}
