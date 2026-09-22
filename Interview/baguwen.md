- Q1 ：Java 中，比较两个 String 时，== 和 .equals() 有什么区别？ 两行分别输出什么，为什么？
```java
String a = new String("hello");
String b = new String("hello");

System.out.println(a == b);
System.out.println(a.equals(b));
```
`a == b` is comparing if they are the same object, `a.equals(b)` is comparing the the content in the string
since each new creates a new object, so these two are differnet objects but both contains "hello". so false

- Q1 followup: what about this time, what does it return? 
```java
String a = new String("hello");
String b = a;
```
return true since we only created one object and b is refering to same object as a.

- Q2: 为什么左边可以写 List，右边写 ArrayList？它们之间是什么关系？
```java
List<Task> tasks = new ArrayList<>();
```
List is an interface, but ArrayList is the implementastion class of the interface. Interface predefines the available operations and functions that should be implemented by the implementation class, and then implementation class provides the real implementation. The variable uses the methods defined in the interface, and the ArrayList actually does the work/operations. This way the code relies on the interface so if we wanan change to use the other List implementation class, no need to change the code that used the methods of the List interface\

- Q3: React 中 props 和 state 有什么区别？结合你的 TaskCard：title 是父组件传来的 prop。expanded 是组件里的 state。它们分别由谁提供或管理？点击 Show Details 时，你应该更新哪一个？

state lives in component, but props is like a variable parent can pass to children to use.
state is managed by the component which defined that. whereas props is managed by the parent component which passes the props to children
when we click show details, state expanded should be updated to true.

- Q4: 为什么我们用 state 保存从后端获取的 tasks，而不是普通变量？
例如，下面这段代码成功获取数据后，为什么不能可靠地让页面更新？
```ts
let tasks: Task[] = []

async function loadTasks() {
  const response = await fetch('http://localhost:8080/tasks')
  tasks = await response.json()
}
```
this is unreliable because even if you are allowed to update the tasks, it wont trigger re-render and thus commits the necessary changes to DOM, thus the page wont change at all. with state, the page will be dynamic and be re-rendered based on the value of the most updated tasks

- Q5: Why must the backend still validate the title? Give an example of how a request could bypass frontend validation.
because we may use postman to test the HTTP request e.g. POST http://localhost:8080/tasks which bypass the frontend validation.

- Q6: Why do we check title == null first? What could happen if we reversed the conditions?
if we check `title.isBlank()` first then there is an error if title is null.

- Q7: Your API returns 201 Created when it creates a task and 400 Bad Request when the title is invalid. Why is returning 200 OK for both outcomes less useful to the frontend?
since we dont know which request is it. it might be GET request returned 200 OK or post request succesfully created a new task so 200 OK, which does not give too much details about the return status

- Q5: When we build the React “Create Task” form, should we clear the title input before sending the POST request, or only after the backend confirms the task was created successfully?
we should only clear the input after the backend confirms the success. Imagine we enter "practice SQL" and click Create.
  - Success: the backend creates the task. React adds it to the displayed list and clears the input, ready for another task.
  - failure: the backend failed to create the task and return failture status code, then react shows error and keeps the title "practice sql" in the input so we can retry without typing it again.

