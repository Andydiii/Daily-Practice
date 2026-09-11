package com.andy.todo_api;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.ArrayList;

// 告诉 Spring：这个 class负责接收 HTTP request并返回 response。
@RestController
public class TaskController {
    @GetMapping("/tasks") // 收到 GET /hello 时，执行下面的 hello() 方法。
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task(1, "Task 1", false);
        Task task2 = new Task(2, "Task 2 new", false);
        tasks.add(task);
        tasks.add(task2);
        return tasks;
    }
}

