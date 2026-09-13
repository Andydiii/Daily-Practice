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
}

export default App