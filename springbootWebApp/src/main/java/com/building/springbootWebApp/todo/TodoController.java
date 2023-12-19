package com.building.springbootWebApp.todo;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@SessionAttributes("name")
public class TodoController {

    private TodoService todoService;
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/list-todos")
    public String listAllTodos(ModelMap modelMap){
        List<Todo> todos = todoService.findByUsername("user");
        modelMap.addAttribute("todos",todos);
        return "listTodos";
    }
    @GetMapping("/add-todo")
    public String showNewTodo(ModelMap modelMap){
        modelMap.addAttribute("todo",new Todo());
        return "addTodo";
    }
    @PostMapping("/add-todo")
    public String addNewTodo(@Valid @ModelAttribute("todo")Todo todo, BindingResult result){
        if(result.hasErrors()){
            return "addTodo";
        }
        todoService.addTodo("user",todo.getDescription(), LocalDate.now(), false);
        return "redirect:/list-todos";
    }
    /*@GetMapping("/add-todo")
    public String showNewTodo() {
        return "addTodo";
    }
    @PostMapping("/add-todo")
    public String addNewTodo(@RequestParam String description){
        todoService.addTodo("user",description, LocalDate.now(), false);
        return "redirect:/list-todos";
    }*/
}