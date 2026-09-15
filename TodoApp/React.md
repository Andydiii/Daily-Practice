jsx is like a languge that we can write html inside javascript.

# props. 
## Example 1
```jsx
// props is the parameter name, TaskCardProps is TypeScript’s syntax to declare the parameter type
// file a defined the Taskcard function
function TaskCard(props: TaskCardProps) {
    return (
        <div>
            <h2>{props.title}</h2>
        </div>
    )
}

// file b use the TaskCard
function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <TaskCard title="practice java"/>
    </>
  )
}

```
Props are values a component receives from its parent—for example, a task’s title.


State is a component’s memory that it can update—for example, whether its details are expanded.

ESLint 代码检查工具



# Type
```jsx
// TaskCardProps is its type—the rules that object must follow.
type TaskCardProps = {
  title: string
}
function TaskCard(props: TaskCardProps) {}

// or we can do this too instead of predefine the type
function TaskCard(props: { title: string }) {}

// the parent level must defined the fields required in the type.
<TaskCard title="Learn React"/>

```


# export

## export default TaskCard vs export function TaskCard() { ... }
```jsx
// With a named export, you can write:
export function TaskCard() {
  return <h2>Learn React</h2>
}
    // then we have to use exact same name with {}.
import { TaskCard } from './TaskCard'


// With a default export, the importing file can choose any name:
export default TaskCard

    // Use it as:
import MyCard from './TaskCard'
<MyCard />
```
## why named export needs a {} but export default does not need one
`{ TaskCard }: “Give me the export named TaskCard.`
`TaskCard without braces: “Give me the default export, and name it TaskCard here.”`

- A file can have several named exports, The braces specify which ones you want:
```jsx
// file a
export function TaskCard() { /* ... */ }
export function TaskButton() { /* ... */ }

// case1: file b only import 
import { TaskCard } from './TaskCard'

// case2: file b import both 
import { TaskCard, TaskButton } from './TaskCard'
```

- A default import doesn’t need braces because a file can have only one default export:
```jsx
// file a that contains TaskCard(), we place the export default at the bottom
export default TaskCard
// file b
import TaskCard from './TaskCard'
```

# State
| 写法                        | 含义                              |
| ------------------------- | ------------------------------- |
| `useState(false)`         | 创建状态，初始值为 `false`，表示未展开         |
| `expanded`                | 当前状态值，之后用它判断是否显示详情              |
| `setExpanded`             | 更新这个状态的函数                       |
| `[expanded, setExpanded]` | 从 `useState` 返回的数组中取出这两个值，叫数组解构 |
```jsx
import { useState } from 'react'
const [expanded, setExpanded] = useState(false)
```


- onClick: tells React what to do when the button is clicked.
- {...}: lets you put a JavaScript expression inside a JSX attribute.
- () => setExpanded(true): a function that React will run when you click.
- setExpanded(true): asks React to update the state and render the component again.
```jsx
<button onClick={() => setExpanded(true)}>
  Show details
</button>
// This means: if expanded is true, display the paragraph; if it’s false, show nothing there.
{expanded && <p>Practise this task for 20 minutes.</p>}
```


## Render - IMPORTANT
- state and render: we defined the state `searchText` in App component, so whenever the state is updated, 
1. rerender: the component App() will be rerender(rerun/recalculation to get the new jsx that describes the UI, this step wont modify any actual DOM elements(real UI) ). 
2. commit: compare the previous render and next render find the difference, commit the necessary changes to actual DOM.
3. Browser displays the result: the browser draws the page using the DOM and styles.

```jsx
function App() {
  const [searchText, setSearchText] = useState('');

  return (
    <>
      <input type="text" placeholder='Search Tasks' value={searchText} onChange={(event) => setSearchText(event.target.value)} />
    </>
  )
}
```

- You need key when rendering a list of elements, like when you use .map() to create several TaskCard components:
```jsx
return (
    <>
      {tasks.map((task) => (
        <TaskCard
          key={task.id}
          title={task.title}
          description={task.description} 
        />
      ))}
    </>
)
```





# syntax
## map
- You need key when rendering a list of elements, like when you use .map() to create several TaskCard components:
```jsx
return (
    <>
      {tasks.map((task) => (
        <TaskCard
          key={task.id}
          title={task.title}
          description={task.description} 
        />
      ))}
    </>
)
```

## filter
```jsx
const filteredTasks = tasks.filter((task) =>
  task.title.includes('React')
)
```

## const
suppose we have `const [tasks, setTasks] = useState<Task[]>([])`
const means we cannot do `tasks = newTasks // 错误：不能给 const 变量重新赋值` and we can only update the value by `setTasks(newTasks)`


# IMPORTANT - async, await, fetch, consume backend API
- `async` allows await and makes the function return a Promise. await pauses that function until the Promise settles(wait until returns a real Response); the rest of the page can keep running. 正常function执行到结尾时：普通函数没有 return → 返回 undefined。async 函数没有 return → 返回一个成功结果为 undefined 的 Promise。TypeScript 把这里的返回类型表示为 Promise<void>，意思是“异步操作完成后，不提供一个供调用者使用的返回值”。
- `fetch()` always returns a Promise. Without waiting or using .then(), treating that Promise as a Response can cause an error. await fetch() gives us the Response object, and await response.json() gives us the parsed data. await pauses the async function, not the entire page. We use async + await to get the real Response before run the follwing code. We usually use async + await when there is a function return a promise. 
- `fetch()` makes a HTTP request, default with GET request. after it finishes, the real response(including the status code, header, and JSON response body)
- `response.ok`: see if status code is 200-299(successful status code), if yes then true, if not in the range it means failed and be false.
- `response.json()`: reads and takes out the response body then parse the JSON into a JS object. e.g. the response body here is an array that contains objects(see JSON below). Then `const data = await response.json()` gives us a JS array containing task objects since `data` is type of `Task[]`. It only cares if the response body is JSON format, if not => error, it only parse the JSON into JS, and it does not detect the type/fields mismatch. even if there is a mismatch in the type/fiednames/number of fields, it does not care. its our responsibity to make sure they are matched. If `description` exist in `Task`  in frontend but does not exist in response body returned by backend, then when we read `data.description` it willl not give error but return `undefined` as result and TS wont fix it for us.
```json
[
  {
    "id": 1,
    "title": "Task 1",
    "completed": false
  },
  {
    "id": 2,
    "title": "Task 2 new",
    "completed": false
  }
]
```

```ts
async function loadTasks() {
  // without using await, fetch returns a Promise not a Response JSON, we assumed we were using real Response JSON but actually we got a Promise and carried Promise forward. 
  
  // so In TypeScript, the editor flags response.ok: Property 'ok' does not exist on type 'Promise<Response>'.
  // At JavaScript runtime, reading that missing property normally returns undefined;
  // response.ok  is undefined => !undefined   // true
  // Therefore, the if condition becomes true, and this line throws the error: throw new Error('Failed to load tasks')
  // we use 
  const response = await fetch('http://localhost:8080/tasks')

  if (!response.ok) {
    throw new Error('Failed to load tasks')
  }

  const data: Task[] = await response.json()
  setTasks(data)
}
```

# error handling
如果没有 try/catch，错误会让 loadTasks() 提前结束，并让它返回的 Promise 变成 rejected（失败）。如果调用它的地方也没有处理这个失败，浏览器 Console 通常会显示： `Uncaught (in promise) ...`
```ts
async function loadTasks() {
  const response = await fetch('http://localhost:8080/tasks')

  if (!response.ok) {
    throw new Error('Failed to load tasks')
  }

  const data = await response.json()
  setTasks(data)
}
```

假设后端返回 500： response.ok 是 false。 执行 throw new Error(...)。
函数停止往下执行，不会运行 response.json() 和 setTasks(data)。
没有人处理这个失败，用户可能只看到“点击按钮没有反应”。 catch 的作用，是让你决定失败后怎么办。 它不会修复网络或自动重试，但可以显示错误、保留数据、提供重试按钮。

例如之后我们可以这样做：
```ts
catch (error) {
  console.error(error)
  setErrorMessage('Could not load tasks. Please try again.')
}
```
这里假设我们已经定义了 errorMessage state：
console.error(error)：给开发者查看具体错误。
setErrorMessage(...)：更新页面状态，让用户看到提示。