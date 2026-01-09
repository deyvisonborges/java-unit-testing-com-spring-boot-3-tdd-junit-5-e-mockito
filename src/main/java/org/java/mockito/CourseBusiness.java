package org.java.mockito;

import java.util.ArrayList;
import java.util.List;

public class CourseBusiness {
    private ICourseService courseService;

    public CourseBusiness(ICourseService courseService) {
        this.courseService = courseService;
    }

    public List<String> retrieveCoursesRelatedToSpring(String student) {
        var filteredCourses = new ArrayList<String>();

        if("For Bar".equals(student)) return filteredCourses;

//        Em runtime, o mockito intercepta o courseService, e mocka a implementacao dele
        var allCourses = courseService.retrieveCourse(student);

        for(String course : allCourses) {
            if(course.contains("Spring")) {
                filteredCourses.add(course);
            }
        }

        return filteredCourses;
    }
}
