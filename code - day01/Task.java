public class Task {
    String title;
    boolean completed;


    // constructor does not need a statc becuae when it gets called, the object is already created with deafult value for each fields.
    public Task(String titile) {
        this.title = titile;
        this.completed = false;
    }
}
