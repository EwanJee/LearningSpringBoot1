package com.building.springbootJPA.course.jdbcRepository;

import com.building.springbootJPA.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseJdbcRepository {

    @Autowired
    public CourseJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private JdbcTemplate jdbcTemplate;

    private static String INSERT_QUERY =
            """
            insert into course values (?,?,?);
                   
            """;
    private static String DELETE_QUERY =
            """
            delete from course where id = ?;
                   
            """;
    private static String SELECT_QUERY =
            """
            SELECT * FROM course WHERE id = ? ;
                   
            """;

    public void insert(Course course){
        jdbcTemplate.update(INSERT_QUERY,course.getId(),course.getName(),course.getAuthor());
    }
    public void delete(Long id){
        jdbcTemplate.update(DELETE_QUERY,id);
    }
    public Course findById(Long id){
        //ResultSet -> Bean =? Row Mapper
        return jdbcTemplate.queryForObject(SELECT_QUERY, new BeanPropertyRowMapper<>(Course.class), id);
    }
}
