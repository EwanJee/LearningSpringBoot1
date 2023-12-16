package com.building.springbootJPA.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseJpaCommandLineRunner implements CommandLineRunner {

    private CourseJpaRepository repository;

    @Autowired
    public CourseJpaCommandLineRunner(CourseJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.insert(new Course(3L,"learn DevOps","SpringBoot"));
        repository.insert(new Course(4L,"learn GCP","SpringBoot"));

    }
}
