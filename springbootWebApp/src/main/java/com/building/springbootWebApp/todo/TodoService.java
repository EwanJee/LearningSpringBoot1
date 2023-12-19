package com.building.springbootWebApp.todo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<>();
    static {
        todos.add(new Todo(1,"user","Learn AWS",
                LocalDate.now().plusYears(1),false));
        todos.add(new Todo(2,"use2","Learn Azure",
                LocalDate.now().plusYears(1),false));
        todos.add(new Todo(3,"user3","Learn DevOps",
                LocalDate.now().plusYears(1),false));
    }
    public List<Todo> findByUsername(String username){
        return todos.stream()
                .filter(x -> Objects.equals(x.getUserName(), username))
                .toList();
    }
    public void addTodo(String username, String description, LocalDate localDate, boolean done){
        todos.add(new Todo(4,username,description,
                localDate,done));
    }
}
