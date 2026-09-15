import { useState } from "react"

type TaskCardProps = {
    title: string
    completed: boolean
}

// props is the parameter name, TaskCardProps is TypeScript’s syntax to declare the parameter type
function TaskCard(props: TaskCardProps) {
    const [expanded, setExpanded] = useState(false);
    return (
        <div>
            <h2>{props.title}</h2>
            <button onClick={() => setExpanded(!expanded)}>
                {expanded? 'Hide Details' : 'Show Details'}
            </button>
            {/*This means: if expanded is true, display the paragraph; if it’s false, show nothing there.*/}
            {expanded && <p>{props.completed? 'Completed': 'Pending'}</p>}
        </div>
    )
}

export default TaskCard