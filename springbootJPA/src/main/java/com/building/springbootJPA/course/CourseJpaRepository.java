package com.building.springbootJPA.course;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CourseJpaRepository {
    @Autowired
    public CourseJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public void insert(Course course){
        entityManager.merge(course);
    }
    public Course findById(Long id){
        return entityManager.find(Course.class, id);
    }
    public void deleteById(Long id){
        entityManager.remove(entityManager.find(Course.class, id));
    }
}
