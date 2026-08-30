package TodoList;
public class Task {
    private String title;
    private boolean completed;


    // constructor does not need a statc becuae when it gets called, the object is already created with deafult value for each fields.
    public Task(String title) {
        this.title = title;
        this.completed = false;
    }

    public void editTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    public void markCompleted() {
        this.completed = true;
    }
}
