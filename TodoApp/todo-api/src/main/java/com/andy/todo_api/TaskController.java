package com.andy.todo_api;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

/*
Our current controller list has two limitations:

Everyone accessing the same running backend would share it.
Restarting the backend would lose its contents.
*/


// 告诉 Spring：这个 class负责接收 HTTP request并返回 response。
// @CrossOrigin(origins = "http://localhost:5173") will make the response contain cors header and then the browser allows the specified origin to read the response body. otherwise browswer does not allow the react app to read the response JSON
@CrossOrigin(origins = "http://localhost:5173") 
@RestController
public class TaskController {
    // when backend starts, the controllers are beans thus gets created automatically by Spring, the field is initialized when startup.
    private List<Task> tasks;
    private int nextId = 3;

    @GetMapping("/tasks") // 收到 GET /tasks 时，执行下面的 hello() 方法。
    public List<Task> getTasks() {
        return tasks;
    }

    // to be a constructor, there should not be a return type.
    public TaskController() {
        this.tasks = new ArrayList<Task>();
        tasks.add(new Task(1, "Task 1", false));
        tasks.add(new Task(2, "Task 2", false));

    }

    // 收到 POST /tasks 时，执行下面的方法。
    // @requestbody converts the incoming request JSON body into CreateTaskRequest 
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED) // Adding this line tells Spring to send HTTP status `201 created` when method completes sucessfully.
    public Task createTask(@RequestBody CreateTaskRequest request) {
        String title = request.getTitle();
        // == compares the refernce, does the title refer to NULL. 
        // .equals() compare the content
        if (title == null || title.isBlank()) {
            // we dont need to handle/catch the exception, Spring will handle it
            throw new ResponseStatusException(
                // status code 400 bad request
                HttpStatus.BAD_REQUEST,
                // reason for the exception
                "Title must not be blank"
            );
        }

        Task newTask = new Task(nextId, title, false);
        tasks.add(newTask);
        nextId++;
        return newTask;
    }
}

