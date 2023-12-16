package com.building.springbootJPA.springdatajpa;

import com.building.springbootJPA.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseSpringDataJpaRepository extends JpaRepository<Course,Long> {

        List<Course> findByAuthor(String author);
}
