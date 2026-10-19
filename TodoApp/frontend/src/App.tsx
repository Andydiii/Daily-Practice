import { useEffect, useState } from 'react'
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
  const [newTaskTitle, setNewTaskTitle] = useState('');
  // disable add button when adding a new task. track the post request
  const [creating, setCreating] = useState(false);
  // disable complete button when completing a task
  const [completing, setCompleting] = useState(false);

  const [csrfToken, setCsrfToken] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  /*
    1. loadTasks() sends GET /tasks with credentials: "include", so the browser sends its JSESSIONID cookie.
    2. The server uses that ID to find the server-side HTTP session. Login changed the session ID and saved an authenticated SecurityContext in that session.
    3. Spring Security loads that context. Your .anyRequest().authenticated() rule checks whether the request has an authenticated user before allowing it to reach TaskController. 
    4. If allowed, getTasks() runs and returns its result.
  */
  async function loadTasks() {
    setErrorMessage('');
    setLoading(true);
    try {
      // const means `response` cannot be updated.
      // fetch returns a promise so we need to wait until the real response is ready before we move forward.
      const response = await fetch("http://localhost:8080/tasks", {
        credentials: "include"
      });

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

    if (newTaskTitle.trim() === '') {
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
        body: JSON.stringify({ title: newTaskTitle.trim() }) // convert js object into a JSON 
      });

      if (!response.ok) {
        throw new Error("Failed to create task");
      }

      const newTask: Task = await response.json();
      setTasks((oldTasks) => { return [...oldTasks, newTask] });
      setNewTaskTitle('');
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
        { method: 'PUT' }
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

  async function handleDelete(id: number) {
    try {
      const response = await fetch(`http://localhost:8080/tasks/${id}`, {
        method: "DELETE"
      });
      if (!response.ok) {
        throw new Error("Failed to delete the task");
      }
      setTasks(tasks.filter((task) => {
        return task.id != id;
      }))
    } catch (error) {
      setErrorMessage("Failed to delete the task")
    }
  }

  async function handleEdit(id: number, title: string) {
      const response = await fetch(`http://localhost:8080/tasks/${id}`, {
        method: "PATCH",
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          title: title
        })
      });
      if (!response.ok) {
        throw new Error("Failed to edit the task");
      }
      const updatedTask = await response.json();
      setTasks(tasks.map((task) => {
        return task.id == id ? updatedTask : task;
      }));
  }

  async function loadCsrfToken() {
    try {
      // The fetch call is not the only part that can fail
      // fetch(...) can reject if the request cannot complete.
      const response = await fetch("http://localhost:8080/auth/csrf", {
        credentials: "include"
      });
      
      // A 404 or 500 response does not make fetch throw. Our if (!response.ok) throw ... handles that case
      if (!response.ok) {
        throw new Error("Could not load the csrf Token");
      }
      const data : { token: string } = await response.json();
      setCsrfToken(data.token);
    } catch (error) {
      setErrorMessage("Could not load the csrf token");
    }
  }

/*
Only the server-side CSRF token is cleared by your login code. React’s csrfToken state does not change automatically. It still contains token A until your frontend calls setCsrfToken(...).
*/
  async function handleLogin() {
    setErrorMessage('');
    try {
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          'X-CSRF-TOKEN': csrfToken,
          "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({email, password}),
      });

      if (!response.ok) {
        throw new Error("failed to login");
      }

      // reached here means there is no error occured and login successfully
      // after login the csrftoken is cleared in backend, but the csrfToken is still there in frontend so we want to clear it and get another csrf token for the signed-in session
      setCsrfToken('');
      await loadCsrfToken();
    } catch (error) {
      setErrorMessage("unable to login");
    }
  }

  // it runs after App mounts, and React Strict Mode may run it twice in development, as you saw earlier
  useEffect(() => {
    loadCsrfToken();
  }, [])

  return (
    <>
      <label htmlFor="email">email</label>
      <input 
        id="email" 
        type="email" 
        placeholder='Enter an Email' 
        value={email} 
        onChange={(event) => setEmail(event.target.value)}
      />
      <label htmlFor="password">Password</label>
      <input 
        id="password" 
        type="password" 
        placeholder='Enter a password' 
        value={password}
        onChange={(event) => setPassword(event.target.value)}
      />
      <button type="button" onClick={handleLogin} disabled={!csrfToken}>
        login
      </button>

      <label htmlFor="task-title">New task title</label>      
      <input
        id="task-title"
        type="text"
        placeholder='Enter a task title'
        value={newTaskTitle}
        onChange={(event) => { setNewTaskTitle(event.target.value) }}
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
          onDelete={handleDelete}
          onEdit={handleEdit}
        />
      ))}
    </>
  )
}

export default App