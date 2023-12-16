package com.building.springbootJPA.springdatajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CourseSpringDataJpaCommandLineRunner implements CommandLineRunner {

    private CourseSpringDataJpaRepository repository;
    @Autowired
    public CourseSpringDataJpaCommandLineRunner(CourseSpringDataJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(repository.findAll());

        System.out.println(repository.findByAuthor("SpringBoot"));
    }
}
