package com.building.springbootJPA.course.jdbcRepository;

import com.building.springbootJPA.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseJdbcCommandLineRunner implements CommandLineRunner {

    private CourseJdbcRepository courseJdbcRepository;

    @Autowired
    public CourseJdbcCommandLineRunner(CourseJdbcRepository courseJdbcRepository) {
        this.courseJdbcRepository = courseJdbcRepository;
    }

    //execute at the startup
    @Override
    public void run(String... args) throws Exception {
        courseJdbcRepository.insert(new Course(1L,"Learn AWS","SpringBoot"));
        courseJdbcRepository.insert(new Course(2L,"Learn Azure","SpringBoot"));

        courseJdbcRepository.delete(1L);

        System.out.println(courseJdbcRepository.findById(2L));
    }
}
