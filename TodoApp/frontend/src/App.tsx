import { useState } from 'react'
import heroImg from './assets/hero.png'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import TaskCard from './TaskCard'
import './App.css'

function App() {
  const tasks = [
    {
      id: 1,
      title: 'Learn React',
      description: 'Practise props and state.'
    },
    {
      id: 2,
      title: 'Practice Java',
      description: 'Solve one array problem.'
    },
    {
      id: 3,
      title: 'Practise SQL',
      description: 'Review GROUP BY and HAVING.'
    }
  ]
  
  const [searchText, setSearchText] = useState('');

  const filteredTasks = tasks.filter((task) =>
    task.title.toLowerCase().includes(searchText.toLowerCase())
  )

  return (
    <>
      <input type="text" placeholder='Search Tasks' value={searchText} onChange={(event) => setSearchText(event.target.value)} />
      <button onClick={() => setSearchText('')}>Clear</button>
      {filteredTasks.length === 0 && <p>No matching tasks.</p>}
      {filteredTasks.map((task) => (
        <TaskCard
          key={task.id}
          title={task.title}
          description={task.description}
        />
      ))}
    </>
  )
}

export default App