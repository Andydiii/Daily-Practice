import { useState } from "react"

type TaskCardProps = {
    id: number
    title: string
    completed: boolean
    onComplete: (id: number) => void
    completing: boolean
    onDelete: (id: number) => void
    onEdit: (id: number, title: string) => void
}

// props is the parameter name, TaskCardProps is TypeScript’s syntax to declare the parameter type
function TaskCard(props: TaskCardProps) {
    const [expanded, setExpanded] = useState(false);
    const [editing, setEditing] = useState(false);
    const [draftTitle, setDraftTitle] = useState(props.title);

    function startEditing() {
        setEditing(true);
    }

    function cancelEditing() {
        setDraftTitle(props.title);
        setEditing(false);
    }

    return (
        <>
            <div>
                {editing ? (
                    <>
                        <label htmlFor="newTitle">New Title</label>
                        <input type="text" id="newTitle" value={draftTitle} onChange={(event) => {setDraftTitle(event.target.value)}}/>
                        <button onClick={() => {
                            props.onEdit(props.id, draftTitle);
                            setEditing(false);
                        }}>save</button>
                        <button onClick={cancelEditing}>cancel</button>
                    </>
                ) : (
                    <>
                        <h2>{props.title}</h2>
                        <button type="button" onClick={startEditing}>Edit</button>
                    </>
                )}
                <button onClick={() => setExpanded(!expanded)}>
                    {expanded ? 'Hide Details' : 'Show Details'}
                </button>
                {/*This means: if expanded is true, display the paragraph; if it’s false, show nothing there.*/}
                {expanded && <p>{props.completed ? 'Completed' : 'Pending'}</p>}
            </div>
            <button 
                onClick={() => {props.onComplete(props.id)}}
                disabled={props.completed || props.completing}
            >
                {props.completed ? 'Completed' : 'Complete'}
            </button>
            <button
                onClick={() => {props.onDelete(props.id)}}
            >
                Delete
            </button>
        </>
    )
}

export default TaskCard