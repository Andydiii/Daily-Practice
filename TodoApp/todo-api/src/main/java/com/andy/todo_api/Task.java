package com.andy.todo_api;

public class Task {
    private int id;
    private String title;
    private boolean completed;

    // constructor 只能
    public Task(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }
}
