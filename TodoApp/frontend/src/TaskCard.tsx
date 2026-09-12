type TaskCardProps = {
    title: string
}

// props is the parameter name, TaskCardProps is TypeScript’s syntax to declare the parameter type
function TaskCard(props: TaskCardProps) {
    return (
        <div>
            <h2>{props.title}</h2>
        </div>
    )
}

export default TaskCard