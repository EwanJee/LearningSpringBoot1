package com.learning.webservices.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.jdbc.support.incrementer.MySQLIdentityColumnMaxValueIncrementer;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "user_details")
public class User {

    @Id @GeneratedValue
    private Integer id;
    @Size(min = 2, message = "Name should have at least 2 characters")
    //@JsonProperty("user_name")
    private String name;
    @Past(message = "Birth Date should be in the past ")
    //@JsonProperty("birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Post> posts;

    public User(Integer id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public User() {

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getId() {
        return id;
    }
}
