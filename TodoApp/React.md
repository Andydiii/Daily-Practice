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


# render
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