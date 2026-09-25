# OPP
## field initializer vs create and fill the fied in constructor:
```java
// With a field initializer, the constructor only adds the starting tasks:
private final List<Task> tasks = new ArrayList<>();

public TaskController() {
    tasks.add(new Task(1, "Task 1", false));
    tasks.add(new Task(2, "Task 2", false));
}

// Or create and fill the list in the constructor:
private final List<Task> tasks;

public TaskController() {
    tasks = new ArrayList<>();

    tasks.add(new Task(1, "Task 1", false));
    tasks.add(new Task(2, "Task 2", false));
}
```

## what is interface. what is the difference between interface and class.
- **Interface**: says which operations/functions are available.
- **Implementation class**: contains the code that performs them.
In java, a class is something that can contain actual data and behavior. An interface is mainly a contract that says what behavior a class must provide. e.g.
```java
public interface Animal {
    void makeSound();
}
```
This says: any class cliam be to an `Animal` must have a `makeSound()` method.


# public/private/protected,  static/non-static method, final:
**public/private/protected**: who can call me? 
**static/non-static:** who does the method belong to? Does the method belong to the class or object?

private method: can only be called inside the class

public method: so the method can be called outside the class. so other methods in other class can also call public method.

protected method: code inside same class e.g. `Task`, other classes in the same package e.g. `com.andy.todo_api`, and subclasses can call it.

normal method: has to create the object first before call the method. e.g. has to create a object twosum first and then twosum.main()

static method: no need to create a object first before call the method. for exmaple. we can do TwoSum.main() without creating TwoSum object.

**final**:
`private final TaskRepository taskRepository;`: final means the field must be assigned once and cannot be assigned a different repository later. We assign it in the constructor: `this.taskRepository = taskRepository;`. After that, this would be a compile error: `this.taskRepository = anotherRepository;`

# Compile/run java program
example if we were to run TodoApp.java
```js
javac TodoApp.java // this compiles TodoApp.java then we got TodoApp.class 
javac ./TodoApp.java 
java TodoApp // this runs the main function in the class TodoApp.
```

# Exception handling
第一种写法：用最细节的exception最稳妥
```java
try {
    // 可能发生 exception 的代码. completedTaskStr given by user input, but user could enter something other than number then its not convertable to number. we need try catch
    int completedTaskNum = Integer.parseInt(completedTaskStr) - 1;
    tasks.get(completedTaskNum).completed = true;
    System.out.println((completedTaskNum + 1) + "th task is completed!");
} catch (NumberFormatException e) {
    // 出现 NumberFormatException 时执行
    // 这里只catch住NumberFormatException这个exception
    // 即使没有使用e 也需要声明这个变量
    System.out.println("Please enter a valid number");
} catch (IndexOutOfBoundsException e) {
    System.out.println("That task does not exist.");
}
```
第二种写法直接用parent class `Exception`，但是一般不建议到处都这样写，因为它会把完全不同的问题混在一起，甚至可能隐藏真正的程序 bug。实际发生的可能不是输入错误，而是代码中的 NullPointerException。但程序却告诉用户是输入错误，这会让 debugging 更困难。\
the try catch below will catch all children class of Exception(parent class). 
- Exception
  - RuntimeException
    - NumberFormatException
    - IndexOutOfBoundsException
    - NullPointerException
```java
try {
    //...
} catch (Exception e) {
    // so this will catch both NumberFormatException and IndexOutOfBoundsException
    System.out.println("Invalid input");
}
```




# Java Data Structure

## Hashmap
Import the HashMap class: `import java.util.HashMap; ` 
initilize a map: `HashMap<String, String> capitalCities = new HashMap<>();`
Add keys and values (Country, City) `capitalCities.put("England", "London");`
Access an Item/replace old with new value: `capitalCities.get("England");`
remove item: `capitalCities.remove("England");`
size: `capitalCities.size();`
loop through a hashmap:
```java
for (String i : capitalCities.keySet()) {
  System.out.println(i);
}

for (String i : capitalCities.values()) {
  System.out.println(i);
}
```
get the value of the key(if there exists one) or return default value: `map.getOrDefault("Apple", 0);`
check existence: `map.containsKey('Apple')`


## Stack(Last in first out)
```java
// Deque means Double-Ended Queue
Deque<Integer> stack = new ArrayDeque<>();

stack.push(10);  // 入栈
stack.push(20);
stack.pop();     // 出栈，得到 20
stack.peek();    // 查看栈顶，得到 10

push()    // 放入栈顶
pop()     // 移除并返回栈顶
peek()    // 查看栈顶
isEmpty() // 是否为空
```

## ArrayList
import java.util.ArrayList;
create a list: `ArrayList<String> lst = new ArratList<>()`
size: `.size()`
get ith element in the list: `lst.get(i)`
add element: `lst.add()`
replace an element: `lst.set(i, "apple")`
update an object: `lst.get(i).fieldName = "newValue"`
check empty: `lst.isEmpty()`
remove element at a specific index: `tasks.remove(i);` (this will return the deleted task)

## Array
Create a new array: 
`String[] cars = new String[] {"Volvo", "BMW", "Ford", "Mazda"};`
// Shortcut (most common) String[] cars = {"Volvo", "BMW", "Ford", "Mazda"}; \
length: `array.length`;

## Scanner
import: `import java.util.Scanner;`
Init scanner: `Scanner scanner = new Scanner(System.in);`
Read a line from user: `String title = scanner.nextLine();`

## String
**type**: must declare as `String` not `string`, there is no `string`
**string comparison**: `DO NOT use == but use str1.equals(str2)`
comapre with a char will alawys return false.
**convert to int**: `Integer.parseInt(str)`
**length**: `str.length()`
**access a index of string**: `str.charAt(i)`
**convert to lower case**: `.toLowerCase()`, time: O(n), space: result in an extra string of length n => O(n)
**remove the nonalphanumeric**: `.reaplceAll("[^a-zA-Z0-9]", "")`, time: O(n), space: result in an extra string of length n => O(n)
**double quotes vs single quotes**: `"a"` is a string of a, `'a'` is a char a
**space**: a string of lengt n causes space of O(n) not O(1).
**immutable & time complexity**: string is immutable in Java, and when we do `str += s.charAt(i)`, each iteration creates a new string and copies the existing string plus the new char. so it costs 1 + 2 + 3 + ... + n time = O(n^2). and each iteration create a new string so 1 + 1 + ... + 1 = O(n)
```java
str = ""
for (int i = 0; i < s.length(); i++) {
    str += s.charAt(i)
}
```



# Encapsulation
## 不允许外部只完成一半操作，从而产生不合理的状态。
```java
/* 
一开始需求只有task.completed = true; 后来产品要求：完成任务时，还要记录完成时间。 原来所有task.completed = true;的地方都得改成
task.completed = true;
task.completedAt = LocalDateTime.now();
你必须找到所有这些地方再逐一添加

而且某个开发者可能只修改了 completed，忘记修改 completedAt
task.completed = true;
// 忘了设置 completedAt
*/

// 使用封装后：
public class Task {
    private boolean completed;
    private LocalDateTime completedAt;

    public void markCompleted() {
        completed = true;
        completedAt = LocalDateTime.now();
    }
}
```

## 防止外部以不完整、不一致的方式修改。
```java
/* 
封装不是为了阻止我们想做的修改，而是为了保证每次修改都遵循统一规则。例如以后“完成任务”不只是修改一个 boolean，还包括：
*/
public void markCompleted() {
    if (completed) {
        return;
    }

    completed = true;
    completedAt = LocalDateTime.now();
    points += 10;
    sendNotification();
}

/*
这时重复完成就可能导致：

重复增加积分
重复发送通知
覆盖原来的完成时间
重复记录日志

所以这里的：

if (completed) {
    return;
}
不是为了阻止正常修改，而是确保“完成任务”这个动作只执行一次。 如果业务上允许重新打开任务，也完全可以提供：
*/
public void reopen() {
    completed = false;
    completedAt = null;
}
/*
外部仍然可以进行我们想要的修改：
*/
task.markCompleted();
task.reopen();
// 只不过不能随便这样修改：
task.completed = false;
// 因为直接修改可能忘记同步清除：completedAt. 从而出现：completed = false, completedAt = 2026-08-23. 任务显示未完成，却仍然有完成时间。
```

## 修改内部实现时，外部代码不需要跟着改
```java
/*
现在 Task 使用：private boolean completed;以后你可能想改成：private String status;

如果 TodoApp 一直直接使用： task.completed
那么修改 field 后，所有使用它的地方都要一起修改。

但如果 TodoApp 使用：task.isCompleted()
那么我们只需要修改 Task 内部：
public boolean isCompleted() {
    return status.equals("COMPLETED");
}

TodoApp 完全不用改变：
String display = task.isCompleted() ? "[x]" : "[ ]";
*/
```

## 更容易排查问题和测试
```java
如果 Task 的状态只能通过：markCompleted() 改变，那么任务状态出现问题时，我们主要检查这个方法。
``` 

# package
## Basics
```java
//是在告诉 Java：这个 class 属于名为 TodoList 的 package（包）。
package TodoList;

public class ContainsDuplicate {
}
//这个 class 的完整名称就不再只是：ContainsDuplicate 而是：TodoList.ContainsDuplicate


// 如果代码写了：
package TodoList;
// 标准结构应该是:
Java practice/
└── TodoList/
    └── ContainsDuplicate.java
// 运行命令时，需要站在 package 文件夹的上一层：
Java practice/
// 然后执行：
javac TodoList/ContainsDuplicate.java
java TodoList.ContainsDuplicate
// 因为 JVM 需要使用完整 class name：
TodoList.ContainsDuplicate
```

## Package in Springboot app
```
src/main/java/          ← Maven 的 Java 源代码目录
com/andy/todo_api/      ← Java package
Task.java               ← class

如果写成：
package java.com.andy.todo_api;
对应路径反而应该是：
src/main/java/java/com/andy/todo_api/Task.java
而且 java.* 是 Java 标准库保留的命名空间，例如：
java.lang
java.util
java.time

com → 组织类型
andy → 个人或公司
todo_api → 项目名称

src/main/java 告诉 Maven“Java 代码从这里开始”，package 只从它后面的目录开始计算。
```



# springboot
## Backend starts: Spring creates object for class registered as beans.
`@RestController` tells Spring to register the class as a bean. When we start the springboot app(which is the backend), Spring creates the objects for all beans automatically. Thus all fields in the beans are initialzed as well.

Other common annotations that register classes as beans include @Service, @Repository, and @Component. We’ll introduce those as the project grows.

e.g. our TaskController:
1. Spring finds TaskController through its annotation.
2. Spring creates a TaskController object, which runs its constructor.
3. Your constructor creates the list and the two sample Task objects.
4. The controller is ready to handle requests.
Later, clicking Load Tasks calls getTasks() on that existing controller.


## HTTP request handle:
### Status code:
`201 Created`
`200 OK`
`400 Bad Request`

### GET: 
when we `return tasks`, Spring Json conversion library 'Jackson', does the conversion automatically from java object to JSON. dont need to call getter ourselve, the library calls the getter to get the value for each field since every field is private.
```java
@RestController
public class TaskController {
    @GetMapping("/tasks")
    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<Task>();
        Task task = new Task(1, "Task 1", false);
        Task task2 = new Task(2, "Task 2 new", false);
        tasks.add(task);
        tasks.add(task2);
        return tasks;
    }
}
```

an HTTP response can’t send Java objects directly. They need to be converted into a format such as JSON. That conversion is called serialization. The getter’s name determines the JSON property name; its return value determines the JSON value.

| Getter          | JSON field    | Value comes from        |
| --------------- | ------------- | ----------------------- |
| `getId()`       | `"id"`        | Calling `getId()`       |
| `getTitle()`    | `"title"`     | Calling `getTitle()`    |
| `isCompleted()` | `"completed"` | Calling `isCompleted()` |

### POST: 
- `@PostMapping("/tasks")`: routes POST /tasks to this method. Your existing GET method handles the same path with a different HTTP method.
- `@RequestBody`: tells Spring to convert the incoming JSON body into a `CreateTaskRequest` object. If incoming request body is empty e.g. `{}` as request json body, then Spring's JSON convertor creates a `CreateTaskRequestor` object called `requestor` using its no-argument constructor. then the fields all default to null. when we use `requestor.getTitle()` we get `null`.
- request: the parameter holding that object. You can read its title with `request.getTitle()`.
- Task: this method will return the newly created task.
- title == null detects a missing title.
- title.isBlank() detects "" or whitespace-only text.
- || skips isBlank() when the title is null.
- new `ResponseStatusException(...)` creates an exception carrying an `HTTP status` and a `reason`.
- throw exits the normal method flow. Spring handles the exception and returns 400.
- `@ResponseStatus(HttpStatus.CREATED)`: Without adding this, if the method completes successfully, backend will send status code `200 ok`. Adding this line will send HTTP status `201 created` when method completes sucessfully.
```java
// 收到 POST /tasks 时，执行下面的方法。
// @requestbody converts the incoming request JSON body into CreateTaskRequest 
@PostMapping("/tasks")
@ResponseStatus(HttpStatus.CREATED) // Adding this line will send HTTP status `201 created` when method completes sucessfully.
public Task createTask(@RequestBody CreateTaskRequest request) {
    String title = request.getTitle();
    // == compares the refernce, does the title refer to NULL. 
    // .equals() compare the content
    if (title == null || title.isBlank()) {
        throw new ResponseStatusException(
            // status code 400
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
```

## CROS problem: CORS(Cross-Origin Resource Sharing) error when loading tasks 
While connecting my React Todo app to a Spring Boot backend, I encountered a CORS(Cross-Origin Resource Sharing) error when loading tasks. I checked the browser console and saw that the backend returned 200 OK, but the browser blocked the frontend from reading the response. The frontend and backend used different ports, so they were different origins. With guidance, I added `@CrossOrigin` to allow my frontend’s origin and restarted the backend. I verified that the tasks loaded successfully, then tested error handling by stopping the backend and confirming that loading worked again after restarting it.

An origin consists of protocol + hostname + port. Your frontend (http://localhost:5173) and backend (http://localhost:8080) have different ports, so they are different origins.

the backend returned the tasks, but its response was missing Access-Control-Allow-Origin. Therefore, the browser blocked your React code from reading the response.

**Why add @CrossOrigin? `@CrossOrigin(origins = "http://localhost:5173")`**

by adding `@CrossOrigin(origins = "http://localhost:5173")` to backend springboot, springboot will add the CORS header to response header automatically then the browser wont block the react app to read the response.

You could write backend code to set response headers manually, but Spring’s CORS support handles that work for you.

**Why can’t React add the permission header?**

Because permission must come from the server sharing the data. A frontend cannot grant itself permission to read another origin’s response. Adding Access-Control-Allow-Origin to your fetch request would not solve this.

**Is browser here same as react app? react app, browser, backend are 3 different things ?**

Yes—React app, browser, and backend are three different things, but the React app runs inside the browser.

| Part          | What it is                                                                                        | In your project                  |
| ------------- | ------------------------------------------------------------------------------------------------- | -------------------------------- |
| **Browser**   | A program that loads pages, runs JavaScript, displays the UI, and enforces browser security rules | Edge                             |
| **React app** | Your frontend JavaScript code running inside the browser                                          | `App`, `TaskCard`, `loadTasks()` |
| **Backend**   | A separate program that receives HTTP requests and returns responses                              | Spring Boot on port `8080`       |

When you click Load Tasks:

Your React code calls `fetch()`.
The browser handles the network request.
Spring Boot receives it and sends a response.
The browser checks CORS before making the response available to your React code.
Your React code reads the JSON and calls setTasks(data).

So the browser can receive the backend response but refuse to let the React app read it. That explains why you saw both 200 OK and a CORS error.

## PUT request(including path variables)
- these two methods both works.
```java
// way 1: only one var
@PutMapping("/tasks/{id}/complete")
public Task completeTask(@PathVariable("id") int id) {
    // ...
}

// way 1: multiple vars
@GetMapping("/tasks/{taskId}/comments/{commentId}")
public Comment getComment(
    @PathVariable int taskId,
    @PathVariable int commentId
) {
    // ...
}

// way 2 
// For /tasks/2/comments/8, taskId is 2 and commentId is 8.
@GetMapping("/tasks/{taskId}/comments/{commentId}")
public Comment getComment(
    @PathVariable("taskId") int taskId,
    @PathVariable("commentId") int commentId
) {
    // ...
}
```



# Data sturcture type delcaration
| Data structure | Primitive allowed? | Example                   |
| -------------- | -----------------: | ------------------------- |
| Array          |              ✅ Yes | `int[] nums`              |
| `List<T>`      |               ❌ No | `List<Integer>`           |
| `Set<T>`       |               ❌ No | `Set<Character>`          |
| `Map<K,V>`     |               ❌ No | `Map<Character, Integer>` |

- Common primitive → wrapper pairs:
int     → Integer
char    → Character
double  → Double
boolean → Boolean
long    → Long
float   → Float
byte    → Byte
short   → Short

# Set up Repository & DB 
1. A dependency is a library Maven downloads for your Java project. We add two dependencies into `pom.xml` because they do different jobs:
**Spring Data JPA**: `spring-boot-starter-data-jpa` helps your code work with stored tasks. Later, Then code such as repository.save(task) can save a task without us writing the basic SQL ourselves. Spring Data JPA provides that programming interface.
**PostgreSQL JDBC driver**: `org.postgresql:postgresql` lets Java communicate with PostgreSQL. It is the JDBC driver: the software that sends database commands to the PostgreSQL server and receives results. JPA needs a driver for the particular database we chose. 
`Your controller → repository/JPA → PostgreSQL driver → todo_app database`

2. added three folowing properties to `TodoApp/todo-api/src/main/resources/application.properties`:
**url**: connect to PostgreSQL on your computer (`localhost`), using port `5432`, and select the `todo_app` database.
**username**: sign in as the PostgreSQL user `postgres`.
**password**: `${DB_PASSWORD}` tells Spring Boot to get the password from a setting outside this file. That way, your password doesn’t go into a file you might commit to Git. Spring Boot supports this placeholder syntax.
```
spring.datasource.url=jdbc:postgresql://localhost:5432/todo_app
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
```

3. Add this line `spring.jpa.hibernate.ddl-auto=update` to `TodoApp/todo-api/src/main/resources/application.properties`:
`ddl-auto` controls what Hibernate does to the database structure when the backend starts. For this local practice app, update tells it to create a missing table or add missing columns based on your entity.

## change to Task.java
```java
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //...

    protected Task() {}
}
```
- `@Entity` tells JPA that Task can be stored as a row in a database table.
- `@Id` marks id as the row’s unique identifier.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)` says the database will generate IDs when we start saving tasks through the repository. Your current controller still supplies IDs for its in-memory list; we’ll remove that later. For example, later our code will create a task with a title but without choosing an ID. When we save it, PostgreSQL generates the ID, and the returned task can include that ID
- `@GeneratedValue` is an annotation on the id field. It tells JPA: “When saving a new Task, generate its ID instead of requiring my code to choose one.”
- `strategy` = names the annotation setting we’re choosing. In Java annotation syntax, it means “set the strategy option to this value.”
- `protected Task() {}` is an empty constructor JPA needs when it builds a Task from a database row. We keep your existing constructor so the current controller still compiles
- `GenerationType` is the type that lists the available ID generation strategies.
- `IDENTITY` is the strategy we chose: let the database generate the ID when it inserts the row. JPA can then put the generated ID back into the saved Task


## TaskRepository.java
`public interface TaskRepository extends JpaRepository<Task, Integer> {}`
- `JpaRepository` is an interface supplied by `Spring Data JPA`. It describes database operations such as `save`, `findAll`, and `findById`.
- `TaskRepository` is your interface. It says, “I want those operations for my Task objects.”
- `Implementation`: There still needs to be an implementation: a class with code that performs those operations. Spring Data creates that implementation when your app starts, so you don’t have to write it yourself. When Spring Boot starts, `Spring Data` creates an implementation for `TaskRepository` that your controller can use.
- `extends`: Because both sides are interfaces, it means TaskRepository inherits the operations declared by JpaRepository. TaskRepository inherits the method declarations from JpaRepository. So a TaskRepository has operations such as save(...) and findById(...) available. The braces can be empty because you don’t need any additional operations yet.
- `JpaRepository<Task, Integer>`: JpaRepository has two type parameters. 
    **First**: the entity(a row in the table. we specified in Task.java) you store, such as Task. 
    **Second**: the Java type of the field marked `@Id`, such as Integer for your Task’s int id. JpaRepository is written for many kinds of data. Those two types tell it which kind you use:
    ```md
    JpaRepository<Task, Integer>
              │     │
              │     └─ type of Task's id
              └─────── type of object being stored
    ```
    or example, Spring then understands save(...) takes a Task, while findById(...) takes a task ID. We write Integer because Java generics use object types; int has the corresponding object type Integer.

- Why need this file?:  It gives your controller a TaskRepository it can call. Later, instead of searching your Java list, the controller can call:  `repository.findById(id);` Spring Data handles that database operation and returns the matching task if one exists. Creating the file alone won’t move data: we still need to change the controller to call the repository.

## TaskController.java

### Constructor & repository
when we run java spring boot app, the beans(e.g. controllers) gets created and initilized automatically. When it creates TaskController Spring supplies the TaskRepository argument(the real implementation) . `this.taskRepository = taskRepository` saves that supplied object in the controller’s field.
```java
public TaskController(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;

    this.tasks = new ArrayList<Task>();
    tasks.add(new Task(1, "Task 1", false));
    tasks.add(new Task(2, "Task 2", false));
}
```

### GET request using findAll()
`findAll()` reads the task rows from PostgreSQL. Your database table is currently empty, so GET will return [] until we update POST to save tasks there. Spring Data supplies the repository method
```java
@GetMapping("/tasks")
public List<Task> getTasks() {
    return taskRepository.findAll();
}
```


### POST request using
`save(newTask)` stores it in the database and returns the saved Task with its generated ID. Your existing @ResponseStatus(HttpStatus.CREATED) still makes a successful POST return 201 Created.
```java
Task newTask = new Task(title, false);
return taskRepository.save(newTask);
```

### PUT request using findById(), orElseThrow()
`orElseThrow`: needs a function that creates exception.
`() -> {}`: is a lambda function but java uses `->` as arrow (js uses `=>`), `()` means it taks no arguments.
`findById(id)`: looks for the task in PostgreSQL.
`save(target)`: writes that change to PostgreSQL and returns the updated task.
```java
@PutMapping("/tasks/{id}/complete")
public Task markCompleted(@PathVariable int id) {
    
    Task target = taskRepository.findById(id).orElseThrow(() -> { 
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Failed to find the task");
    });

    target.setCompleted();
    return taskRepository.save(target);
}
```

### When does .save() update the exisiting task VS create a new task into DB?
- `save()` looks at the Task’s ID to choose a path. With your current int id and no version field, the simplified logic is:
- `save(new Task("Learn SQL", false))` starts with ID 0 and takes the create path. save(target) receives the task you already loaded with findById(id), so it has an ID and takes the update path. Internally, Spring Data JPA uses persist() for a new entity and merge() for one it considers existing

```java
if (task.getId() == 0) {
    // New task: insert it
} else {
    // Existing task: merge its changes
}
```