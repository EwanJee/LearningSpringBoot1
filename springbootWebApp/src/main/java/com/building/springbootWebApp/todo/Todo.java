package com.building.springbootWebApp.todo;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class Todo {
    public Todo(int id, String userName, String description, LocalDate targetDate, boolean done) {
        this.id = id;
        this.userName = userName;
        this.description = description;
        this.targetDate = targetDate;
        this.done = done;
    }
    public Todo() {
    }

    private int id;
    private String userName;
    @Size(min = 10, message = "Please Enter at least 10 characters")
    private String description;
    private LocalDate targetDate;
    private boolean done;

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", description='" + description + '\'' +
                ", targetDate=" + targetDate +
                ", done=" + done +
                '}';
    }
}
