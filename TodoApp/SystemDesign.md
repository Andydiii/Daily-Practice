- 如果希望 React 页面显示 Spring Boot 返回的任务，而不是写死的数组，React 应该向哪个地址发送什么 HTTP 请求？
GET /tasks.
complete url is: http://localhost:8080/tasks

- Spring Boot 返回任务的 JSON 数组之后，React 应该怎样处理这些数据，才能把它们显示成 TaskCard？用自己的话描述即可
1. React sends the `GET /tasks` to backend
2. Spring Boot receives the request and then returns a JSON response
3. React parses the JSON into a JavaScript array of tasks.
4. It saves the array in a `tasks` state variable.
5. React rerenders the component, using `.map()` to turn those tasks into `TaskCard` components. 

- why would we store the returned tasks in state instead of only in an ordinary variable?
since we may add a new task, delete a task, or edit a task, in which if we use state to store tasks will rerender and commit the necessary changes to DOM to show updated UI, thus reflect the updated tasks. e.g. a Add Task flow would be:
```
1. user adds a task with title
2. React sends `POST /task` with the title
3. Spring Boot validates the input and saves the task in DB.
4. Spring Boot returned the created task, including its ID.
5. React updates its tasks state to display it. 
```

- If saving a task fails, what should the page show, and should it keep or clear the title the user entered?
if task failed to save, then we should pop up a error message telling user the failure, and should keep the title user entered in the form. If the database write failed, the task shouldn’t be saved or shown as successfully added.

- In our planned Todo application, what should each part be responsible for?
React frontend - displays the UI, collects user input, sends HTTP requests, and shows results or errors.
Spring Boot backend - receives requests, validates input, applies business rules, reads/writes database data, and returns responses.
Database - persistently stores data and supports queries and constraints, such as unique IDs.

- suppose React already prevents users from submitting an empty task title. Does Spring Boot still need to check whether the title is empty? Why? Hint: is the React page the only way someone can send an HTTP request to your backend?

SpringBoot still need validatiion on whether the tot;e os empty since we may send request from postman, bypassing React’s validation.

So both checks serve a purpose:
Frontend validation: gives users immediate feedback.
Backend validation: enforces the rule regardless of who sends the request.

For example, Spring Boot should reject a missing, empty, or whitespace-only title, usually with 400 Bad Request, and avoid saving it.

- explain the complete Add Task flow in your own words—from the user entering a title and clicking Add, through Spring Boot and the database, to the updated React UI. Include what happens if validation or saving fails.

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
