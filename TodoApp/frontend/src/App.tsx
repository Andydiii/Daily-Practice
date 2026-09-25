import { useState } from 'react'
import TaskCard from './TaskCard'
import './App.css'

// the field names in Task must match field names returned in the JSON from backend.
// the field names in props does not need to match the field names in APP nor the backend JSON response.
type Task = {
  id: number
  title: string
  completed: boolean
}

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [searchText, setSearchText] = useState('');
  
  // disable load button when its loading tasks. track the get request
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [title, setTitle] = useState('');
  // disable add button when adding a new task. track the post request
  const [creating, setCreating] = useState(false);
  // disable complete button when completing a task
  const [completing, setCompleting] = useState(false);

  async function loadTasks() {
    setErrorMessage('');
    setLoading(true);
    try {
      // const means `response` cannot be updated.
      // fetch returns a promise so we need to wait until the real response is ready before we move forward.
      const response = await fetch("http://localhost:8080/tasks");

      if (!response.ok) {
        throw new Error("Failed to load tasks");
      }

      const data: Task[] = await response.json();

      setTasks(data);
    } catch (error) {
      console.error(error)
      setErrorMessage('Could not load tasks. Please try again.')
    } finally {
      // finally block will get run no matter try is suceesful or failed
      setLoading(false)
    }
  }

  const filteredTasks = tasks.filter((task) =>
    task.title.toLowerCase().includes(searchText.toLowerCase())
  )


  async function createTask() {
    setErrorMessage('');

    if (title.trim() === '') {
      setErrorMessage('Please enter a valid task title');
      return;
    }

    setCreating(true);

    try {
      const response = await fetch('http://localhost:8080/tasks', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }, 
        body: JSON.stringify({title: title.trim()}) // convert js object into a JSON 
      });

      if (!response.ok) {
        throw new Error("Failed to create task");
      }

      const newTask: Task = await response.json();
      setTasks((oldTasks) => {return [...oldTasks, newTask]});
      setTitle('');
    } catch (error) {
      console.error(error);
      setErrorMessage("Could not create task. Please try again");
    } finally {
      setCreating(false);
    }
  }

  async function handleComplete(id: number) {
    setCompleting(true);
    try {
      const response = await fetch(
        `http://localhost:8080/tasks/${id}/complete`, 
        {method: 'PUT'}
      );
      if (!response.ok) {
        throw new Error("Failed to complete the task");
      }
      const updatedTask: Task = await response.json();
      setTasks(tasks.map((task) => {
        return task.id === id ? updatedTask : task;
      }));
    } catch (error) {
      setErrorMessage("Could not update the task, please try again");
    } finally {
      setCompleting(false);
    }
  }

  return (
    <>
      <label htmlFor="task-title">New Task Title</label>
      <input
        id="task-title"
        type="text"
        placeholder='Enter a task title'
        value={title}
        onChange={(event) => {setTitle(event.target.value)}}
      />
      <button type='button' onClick={createTask} disabled={creating}>
        {creating ? 'Creating...' : 'Add a Task'}
      </button>
      <button onClick={loadTasks} disabled={loading}>
        {loading ? 'Loading...' : 'Load Tasks'}
      </button>
      {errorMessage && <p role='alert'>{errorMessage}</p>}
      <input type="text" placeholder='Search Tasks' value={searchText} onChange={(event) => setSearchText(event.target.value)} />
      <button onClick={() => setSearchText('')}>Clear</button>
      {filteredTasks.length === 0 && <p>No matching tasks.</p>}
      {filteredTasks.map((task) => (
        <TaskCard
          key={task.id}
          id={task.id}
          title={task.title}
          completed={task.completed}
          onComplete={handleComplete}
          completing={completing}
        />
      ))}
    </>
  )
}

export default App