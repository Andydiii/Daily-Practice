package com.andy.todo_api;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.net.Authenticator;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Authentication represents the current request’s login information, including the principal we stored during login.
import org.springframework.security.core.Authentication;

/*
Our current controller list has two limitations:

Everyone accessing the same running backend would share it.
Restarting the backend would lose its contents.
*/



// @CrossOrigin(origins = "http://localhost:5173") will make the response contain cors header and then the browser allows the specified origin to read the response body. otherwise browswer does not allow the react app to read the response JSON
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
// 告诉 Spring：这个 class负责接收 HTTP request并返回 response。
@RestController
public class TaskController {
    // similar to List<...> lst = new arrayList<>(); List is an interface and we will assign a implementation to it.
    private final TaskRepository taskRepository;
    private final AppUserRepository appUserRepository;

    // 收到 GET /tasks 时，执行下面的 hello() 方法。
    @GetMapping("/tasks") 
    // You already imported Authentication for task creation
    public List<Task> getTasks(
        Authentication authentication,
        @RequestParam(name = "completed", required = false) Boolean completed
        /*
            RequestParam lets Spring read a value from the URL’s query string, such as ?completed=true.
            - name = "completed" identifies the URL parameter to read.
            - required = false' means this filter is not mandatory. this allows user can do `get /tasks` too
            - Spring converts the URL text "true" or "false" into a Boolean value.

            - why Boolean instead of boolean?
            Primitive boolean only supports true and false. The wrapper Boolean also supports null, meaning “no filter supplied.”
            | Request | `completed` value | Query used |
            |---|---|---|
            | `/tasks` | `null` | All tasks for this user |
            | `/tasks?completed=true` | `true` | Completed tasks for this user |
            | `/tasks?completed=false` | `false` | Incomplete tasks for this user |
        */
    ) {
        // has to use (Integer) since authentication.getPrincipal() will return a Object. we need to cast it to Integer and then java automatically convert Integer to an int.
        int userId = (Integer) authentication.getPrincipal();
        // user wants to load all tasks
        if (completed == null) {
            return taskRepository.findByOwner_Id(userId);
        }

        return taskRepository.findByOwner_IdAndCompleted(userId, completed);
    }

    // taskController asks for taskRepository in its constructor, and Spring supplies it.
    public TaskController(
        TaskRepository taskRepository,
        AppUserRepository appUserRepository
    ) {
        this.taskRepository = taskRepository;
        this.appUserRepository = appUserRepository;
    }

    // 收到 POST /tasks 时，执行下面的方法。
    // @requestbody converts the incoming request JSON body into CreateTaskRequest 
    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED) // Adding this line tells Spring to send HTTP status `201 created` when method completes sucessfully.
    public Task createTask(
        @RequestBody CreateTaskRequest request,
        Authentication authentication
    ) {
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

        /*
            why we cannot directly do 
            int userId = authentication.getPrincipal();

            our originally supplied an int in principal when login, but Java stores it as an Integer object because the principal is an Object. When retrieving it, getPrincipal() declares its return type as Object, even though the object inside is an Integer. Therefore, this fails to compile: int userId = authentication.getPrincipal() since Java sees: int = Object. (Integer) casts that Object to Integer, because we know your login code stored an integer user ID. Java automatically converts the Integer object into the primitive int used by userId. This is called unboxing.

            HTTP session
                → SecurityContext
                    → UsernamePasswordAuthenticationToken
                        → principal: userId
                        → credentials: null
                        → authorities: empty list
        */ 
        int userId = (Integer) authentication.getPrincipal();

        AppUser owner = appUserRepository.findById(userId).orElseThrow(() -> {
            return new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "User does not exist"
            );
        });

        Task newTask = new Task(title, false, owner);
        return taskRepository.save(newTask);
    }

    
    // 收到 PUT /tasks/{id}/complete 时，执行下面的方法
    // {id} is the number in the URL; @PathVariable int id gives that number to the method.
    @PutMapping("/tasks/{id}/complete")
    public Task markCompleted(
        @PathVariable int id,
        Authentication authentication
    ) {
        int userId = (Integer) authentication.getPrincipal();

        // orElseThrow needs a function that creates exception
        // () means it taks no arguments
        // If it doesn’t exist or belongs to someone else, we return 404.
        Task target = taskRepository.findByIdAndOwner_Id(id, userId).orElseThrow(() -> { 
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to find the task");
        });

        target.setCompleted();
        return taskRepository.save(target);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(
        @PathVariable int id,
        Authentication authentication
    ) {
        int userId = (Integer) authentication.getPrincipal();

        Task task = taskRepository.findByIdAndOwner_Id(id, userId).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to find the task");
        });
        // when we reach here, we found the task
        taskRepository.delete(task);
    }

    // for updating existing task's title only.
    @PatchMapping("/tasks/{id}")
    public Task editTitle(
        @PathVariable int id, 
        @RequestBody UpdateTaskTitleRequest request,
        // Authentication authentication gives us the signed-in identity.
        Authentication authentication
    ) {
        String title = request.getTitle();

        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST,
              "You must enter a valid title"  
            );
        }

        int userId = (Integer) authentication.getPrincipal();

        // findByIdAndOwner_Id(id, userId) retrieves the task only if it belongs to that user. Otherwise, it returns no match and we send 404.
        Task task = taskRepository.findByIdAndOwner_Id(id, userId).orElseThrow(() -> {
            return new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                "Failed to find the task"
            );
        });
        
        task.setTitle(title);
        return taskRepository.save(task);
    }
}

