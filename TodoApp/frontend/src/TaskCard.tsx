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
    const [saving, setSaving] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    function startEditing() {
        setDraftTitle(props.title);
        setEditing(true);
    }

    function cancelEditing() {
        setDraftTitle(props.title);
        setEditing(false);
    }

    async function handleSave() {
        // validate the the input title is not full of space and empty.
        if (draftTitle.trim() == '') {
            setErrorMessage("Please enter a valid title");
            return;
        }

        // set saving to true make button disabled
        setSaving(true);
        // reset errormessage since previous error message still exists.
        setErrorMessage("");

        try {
            // update the title to the new title user entered. may fail
            await props.onEdit(props.id, draftTitle);
            // saving successful, set editing to false to make the view back to not editing mode.
            setEditing(false);
        } catch (error) {
            setErrorMessage("Failed to update the title");
        } finally {
            // no matter finished/failed saving, Make save button available.
            setSaving(false);
        }
    }

    return (
        <>
            <div>
                {/* if user is editing */}
                {editing ? (
                    <>
                        {errorMessage && <p role='alert'>{errorMessage}</p>}
                        <label htmlFor="newTitle">New Title</label>
                        <input type="text" id="newTitle" value={draftTitle} onChange={(event) => {setDraftTitle(event.target.value)}}/>
                        <button disabled={saving} onClick={handleSave}>save</button>
                        <button onClick={cancelEditing}>cancel</button>
                    </>
                ) : (
                    /* if user is not editing */
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