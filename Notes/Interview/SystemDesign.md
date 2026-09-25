# Frontend + Backend system design:
## 如果希望 React 页面显示 Spring Boot 返回的任务，而不是写死的数组，React 应该向哪个地址发送什么 HTTP 请求？
GET /tasks.
complete url is: http://localhost:8080/tasks

## Spring Boot 返回任务的 JSON 数组之后，React 应该怎样处理这些数据，才能把它们显示成 TaskCard？用自己的话描述即可
1. React sends the `GET /tasks` to backend
2. Spring Boot receives the request and then returns a JSON response
3. React parses the JSON into a JavaScript array of tasks.
4. It saves the array in a `tasks` state variable.
5. React rerenders the component, using `.map()` to turn those tasks into `TaskCard` components. 

## why would we store the returned tasks in state instead of only in an ordinary variable?
since we may add a new task, delete a task, or edit a task, in which if we use state to store tasks will rerender and commit the necessary changes to DOM to show updated UI, thus reflect the updated tasks. e.g. a Add Task flow would be:
```
1. user adds a task with title
2. React sends `POST /task` with the title
3. Spring Boot validates the input and saves the task in DB.
4. Spring Boot returned the created task, including its ID.
5. React updates its tasks state to display it. 
```

## If saving a task fails, what should the page show, and should it keep or clear the title the user entered?
if task failed to save, then we should pop up a error message telling user the failure, and should keep the title user entered in the form. If the database write failed, the task shouldn’t be saved or shown as successfully added.

## In our planned Todo application, what should each part be responsible for?
React frontend - displays the UI, collects user input, sends HTTP requests, and shows results or errors.

Spring Boot backend - receives requests, validates input, applies business rules, reads/writes database data, and returns responses.
Database - persistently stores data and supports queries and constraints, such as unique IDs.

## suppose React already prevents users from submitting an empty task title. Does Spring Boot still need to check whether the title is empty? Why? Hint: is the React page the only way someone can send an HTTP request to your backend?

SpringBoot still need validatiion on whether the tot;e os empty since we may send request from postman, bypassing React’s validation.

So both checks serve a purpose:
Frontend validation: gives users immediate feedback.
Backend validation: enforces the rule regardless of who sends the request.

For example, Spring Boot should reject a missing, empty, or whitespace-only title, usually with 400 Bad Request, and avoid saving it.

##  explain the complete Add Task flow in your own words—from the user entering a title and clicking Add, through Spring Boot and the database, to the updated React UI. Include what happens if validation or saving fails.

Add Task Flow:
1. user enter the create task form and click create. React Validates the value in field.
    - if failed, show error and keep input, no request will be sent.
    - if successfuly, React sends the HTTP request `POST /tasks` with the JSON body e.g. {"title": "Learn SQL"}
2. SpringBoot receives the HTTP request, validate
    - if failed, show an error message without saving it in DB, and display error on react UI and keep input
    - if validation is succesful, SpringBoot trys saves the new Task into DB. 
3. DB:
    - if save failed, Springboot returend error response, and React should display a pop up/inline error message on UI, and the title entered in creation form shoule be kept as it is.
    - if save successful, SpringBoot return JSON with created new Task to React and status code `201 created` and do step 4
4. React receives the returned new Task JSON, then update the tasks state to inlcude the new task, then rerender the component and commit the necessary changes to DOM elements.

## If you create three new tasks and then restart Spring Boot, what will GET /tasks return? Why?
when we create 3 new tasks by `POST /tasks`, there are 5 tasks intotal in the tasks. If we were to restart the spring boot, the tasks stored in memory are lost, and constructor gets called again to inilialize the `tasks` to add the 2 initial tasks. So now `Get /Tasks` only returns two tasks(Task 1 and Task 2) inilized in the constructor. 

## We want newly created tasks to remain available after restarting the backend. Where should we store them instead of relying only on the ArrayList?
We should store them in the DB so we dont lost them when backend restarts.

## Suppose the backend tries to save a new task, but the database write fails. Should it return 201 Created? What should React do with the title the user entered?

No. it should return a failed status code to frontend and react should display an error and keep the title so user could retry creating a new task.

## When we later store Todo tasks in a database, we might split the backend into a controller, service, and repository. For POST /tasks, what do you think each one should do?
Controller: receives the HTTP request and sends the HTTP response.
Service: applies the app’s rules, such as rejecting a blank title and creating the task.
Repository: saves the task to the database.
Your current TaskController does all three jobs itself. That works for practice; splitting it up will make more sense as we add a database.

# Design Todo Database Tabel

**We want to store tasks in a database. Each task has an id, a title, and a completed status. Which field would you choose as the primary key, and why would you choose it instead of title?** \
A: Of course ID, since it is unique identifier which we can use to identify each task. not title or completed status bc a task may have >= 1 title or completed status. A primary key must be unique and cannot be NULL.

**For the title column, should we allow NULL, an empty string (""), or whitespace-only text (" ")?Would a database NOT NULL constraint reject all three? Explain your reasoning.**

I think we should not allow any of the three because they dont make sense. `NOT NULL` constraint does not reject all three. It only rejects `NULL`. It does not reject `''` or `' '`. An empty string or spaces are still values, so they satisfy `NOT NULL`

we should reject all by this way
```java
if (title == null || title.isBlank()) {
    // Reject the title
}
```

**Quick check: Why would title.isBlank() || title == null be unsafe?**

It’s unsafe because title might be null, and you cannot call a method on null.Java evaluates || from left to right. Consider:
`title.isBlank() || title == null`. If title is null: Java tries to execute title.isBlank(). It throws a NullPointerException. It never reaches title == null.


**When creating a task, the frontend sends only: `{"title": "Learn SQL"}` What default value should the backend use for completed, and should that database column allow NULL? Why?**
frontend sends HTTP POST request with body includes the title only to backend. backend should have default value `false` for completed since its a new task. No, it should not allow `NULL` for completed in DB since a task is either `completed` or `incompleted`.

**so far we have this plan for DB design**
| Field       | Rule                                                |
| ----------- | --------------------------------------------------- |
| `id`        | Primary key: unique and non-null                    |
| `title`     | Non-null; also reject empty or whitespace-only text |
| `completed` | Non-null, defaults to `false`                       |


 


