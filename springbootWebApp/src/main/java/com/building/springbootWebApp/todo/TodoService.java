package com.building.springbootWebApp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<>();
    static {
        todos.add(new Todo(1,"user","Learn AWS",
                LocalDate.now().plusYears(1),false));
        todos.add(new Todo(2,"user","Learn Azure",
                LocalDate.now().plusYears(1),false));
        todos.add(new Todo(3,"user2","Learn DevOps",
                LocalDate.now().plusYears(1),false));
    }
    public List<Todo> findByUsername(String username){
        /*return todos.stream()
                .filter(x -> Objects.equals(x.getUserName(), username))
                .toList();*/
        Predicate<? super Todo> predicate = todo -> todo.getUserName().equalsIgnoreCase(username);
        return todos.stream().filter(predicate).toList();
    }
    public void addTodo(String username, String description, LocalDate localDate, boolean done){
        todos.add(new Todo(4,username,description,
                localDate,done));
    }
    public void deleteById(int id){
        // todo -> todo.getId() == id
        Predicate<Todo> predicate
                = (todo) -> todo.getId() == id;

        todos.removeIf(predicate);
    }

    public Todo findById(int id) {
        Predicate<Todo> predicate
                = (todo) -> todo.getId() == id;
        return todos.stream()
                .filter(predicate)
                .findFirst()
                .get();
    }

    public void update(@Valid Todo todo) {
        deleteById(todo.getId());
        todo.setUserName("user");
        todos.add(todo);
    }
}
