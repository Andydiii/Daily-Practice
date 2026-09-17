# OPP
what is interface. what is the difference between interface and class.
In java, a class is something that can contain actual data and behavior. An interface is mainly a contract that says what behavior a class must provide. e.g.
```java
public interface Animal {
    void makeSound();
}
```
This says: any class cliam be to an `Animal` must have a `makeSound()` method.


# public/private static/non-static method:
**public/private**: who can call me? \
**static/non-static:** who does the method belong to? Does the method belong to the class or object?

private method: can only be called inside the class

public method: so the method can be called outside the class. so other methods in other class can also call public method.

normal method: has to create the object first before call the method. e.g. has to create a object twosum first and then twosum.main()

static method: no need to create a object first before call the method. for exmaple. we can do TwoSum.main() without creating TwoSum object.

# Compile/run java program
example if we were to run TodoApp.java
```js
javac TodoApp.java // this compiles TodoApp.java then we got TodoApp.class 
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
## Spring Json conversion library 'Jackson', does the conversion automatically from java object to JSON. dont need to call getter ourselve, the library calls the getter to get the value for each field since every field is private.
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

## an HTTP response can’t send Java objects directly. They need to be converted into a format such as JSON. That conversion is called serialization. The getter’s name determines the JSON property name; its return value determines the JSON value.

| Getter          | JSON field    | Value comes from        |
| --------------- | ------------- | ----------------------- |
| `getId()`       | `"id"`        | Calling `getId()`       |
| `getTitle()`    | `"title"`     | Calling `getTitle()`    |
| `isCompleted()` | `"completed"` | Calling `isCompleted()` |

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