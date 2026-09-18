package com.andy.todo_api;

// this is the class for converting the incoming json body into.
public class CreateTaskRequest {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
