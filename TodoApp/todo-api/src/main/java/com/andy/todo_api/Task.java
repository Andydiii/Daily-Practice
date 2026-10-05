package com.andy.todo_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; // automatically 0 for a new task since Java automatically initializes instance fields: an int starts at 0 even if you don’t assign it.
    private String title;
    private boolean completed;

    // non-argument ctor still works when we have another ctor.
    // if we have the other ctor, java does not provie the Task() {} anymore.
    protected Task() { 

    }

    public Task(String title, boolean completed) {
        this.title = title;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted() {
        this.completed = true;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
