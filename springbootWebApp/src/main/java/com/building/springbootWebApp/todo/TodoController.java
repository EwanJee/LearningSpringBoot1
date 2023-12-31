package com.building.springbootWebApp.todo;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        String username = getLoggedUsername(modelMap);
        List<Todo> todos = todoService.findByUsername(username);
        modelMap.addAttribute("todos",todos);
        return "listTodos";
    }

    private String getLoggedUsername(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/add-todo")
    public String showNewTodo(ModelMap modelMap){
        modelMap.addAttribute("todo",new Todo());
        return "addTodo";
    }
    @PostMapping("/add-todo")
    public String addNewTodo(@Valid @ModelAttribute("todo")Todo todo, BindingResult result, ModelMap modelMap){
        if(result.hasErrors()){
            return "addTodo";
        }
        String username = getLoggedUsername(modelMap);
        todo.setUserName(username);
        todoService.addTodo("user",todo.getDescription(), LocalDate.now(), todo.isDone());
        return "redirect:/list-todos";
    }
    @GetMapping("/delete-todo/{id}")
    public String deleteTodo(@PathVariable(name = "id") int id){
        todoService.deleteById(id);
        return "redirect:/list-todos";
    }
    @GetMapping("/update-todo/{id}")
    public String showUpdateTodo(@PathVariable(name = "id") int id, ModelMap modelMap){
        Todo todo = todoService.findById(id);
        modelMap.addAttribute("todo",todo);
        return "addTodo";
    }
    @PostMapping("/update-todo/{id}")
    public String updateTodo(@Valid @ModelAttribute("todo") Todo todo, BindingResult result, ModelMap modelMap){
        if(result.hasErrors()){
            return "addTodo";
        }
        String username = getLoggedUsername(modelMap);
        todo.setUserName(username);
        todoService.update(todo);
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
