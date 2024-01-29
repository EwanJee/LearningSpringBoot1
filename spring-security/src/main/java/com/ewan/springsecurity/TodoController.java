package com.ewan.springsecurity;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    private static final List<Todo> TODOS = List.of(
            new Todo("ewan", "Learn Spring Security"),
            new Todo("ewan", "Learn Spring Boot")
    );

    @GetMapping("/todo")
    public List<Todo> getTodo() {
        return TODOS;
    }
    @GetMapping("/users/{username}/todo")
    @PreAuthorize("hasRole('USER') and #username == authentication.name")
    @PostAuthorize("returnObject.username() == 'ewan' ")
    @RolesAllowed({"ADMIN","USER"})
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public Todo getTodo(@PathVariable String username) {
        return TODOS.get(0);
    }
    @PostMapping("/users/{username}/todo")
    public void createTodo(@PathVariable String username, @RequestBody Todo todo) {
        
    }

}

record Todo(String username, String description){}
