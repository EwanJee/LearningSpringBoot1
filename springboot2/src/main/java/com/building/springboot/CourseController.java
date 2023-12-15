package com.building.springboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class CourseController {

    // courses
    // Course : id, name, author
    @RequestMapping("/courses")
    public List<Course> retrieveAllCourses(){
        return Arrays.asList(
                new Course(0,"Learn AWS","SpringBoot"),
                new Course(1,"Learn DevOps","SpringBoot"),
                new Course(2,"Learn Azure","SpringBoot")
        );
    }
}
