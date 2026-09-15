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
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

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

      const data:Task[] = await response.json();
      
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




  return (
    <>
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
          title={task.title}
          completed={task.completed}
        />
      ))}
    </>
  )
}

export default App