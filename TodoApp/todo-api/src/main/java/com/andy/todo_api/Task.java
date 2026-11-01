package com.andy.todo_api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; // automatically 0 for a new task since Java automatically initializes instance fields: an int starts at 0 even if you don’t assign it.
    private String title;
    private boolean completed;

    /* 
        @ManyToOne: Many tasks can belong to one user. Each task has one owner. optional = false tells JPA that every task must have an owner.

        fetch = FetchType.LAZY: Requests that JPA load the owner’s data when needed, instead of automatically fetching it with every task. e.g. 
        A task and its owner are stored in separate database tables.
        Suppose you retrieve a task: 
            Task task = taskRepository.findById(1).orElseThrow();
        You might only need its title:
            System.out.println(task.getTitle());
        In that case, loading the owner’s email and password hash would be unnecessary.
            fetch = FetchType.LAZY
        Tells JPA: “When loading a task, defer loading the associated user’s details until they are needed.”
        For example, if we later add an owner getter:
            task.getTitle();             // Uses the task's data.
            task.getOwner().getEmail();  // May trigger loading the owner's data.

        @JoinColumn(name = "user_id"): 
        nullable = false describes the database column as required.
        
        Maps the relationship to a `user_id` column in the task table, referencing the user’s primary key.
        In Java, you store an object reference: private AppUser owner;
        But a relational database represents this relationship using an ID, rather than putting the whole user object inside the task row.

        Your tables could look like this:
        app_user table:
        | id | email | password_hash |
        | 5 | andy@example.com | encoded-password |
        | 8 | bob@example.com | encoded-password |

        task table:
        | id | title | completed | user_id |
        |---|---|---|---|
        | 1 | Practice Java | false | 5 |
        | 2 | Review SQL | false | 5 |
        | 3 | Buy groceries | false | 8 |

        @JsonIgnore: 
        what appears in the HTTP response. 
        Your controller returns Java objects, and Jackson converts them into JSON: 
            return task;
        We want the frontend to receive:
        {
            "id": 1,
            "title": "Practice Java",
            "completed": false
        }
        We do not want it to receive the associated user’s password hash:
        {
            "id": 1,
            "title": "Practice Java",
            "completed": false,
            "owner": {
                "id": 5,
                "email": "andy@example.com",
                "passwordHash": "encoded-password"
            }
        }
    */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private AppUser owner; // owner is initially null, which is Java’s default value for an object-reference field
 

    // non-argument ctor still works when we have another ctor.
    // if we have the other ctor, java does not provie the Task() {} anymore.
    protected Task() { 

    }

    public Task(String title, boolean completed, AppUser owner) {
        this.title = title;
        this.completed = completed;
        this.owner = owner;
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

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }
}