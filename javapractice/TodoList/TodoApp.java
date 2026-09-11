package TodoList;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {
    private ArrayList<Task> tasks = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    private void listTasks() {
        System.out.println("You have selected List Tasks");
        if (tasks.isEmpty()) {
            System.out.println("There are no tasks");
        }
        String status;
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.get(i);
            status = currTask.isCompleted() ? "[x]" : "[ ]";
            System.out.println((i + 1) + ". " + status + " " + currTask.getTitle());
        }
    }

    private void addTask() {
        System.out.println("Please enter the task title: ");
        String title = scanner.nextLine();
        if (title.trim().isEmpty()) {
            System.out.println("Task Title cannot be empty");
            return;
        }
        Task task = new Task(title);
        tasks.add(task);
        System.out.println("You have added a task successfully!");
    }

    private void completeTask() {
        System.out.println("Which Task did you complete: ");
        String completedTaskStr = scanner.nextLine();
        try {
            int completedTaskNum = Integer.parseInt(completedTaskStr) - 1;
            // user entered valid complete number 
            tasks.get(completedTaskNum).markCompleted();
            System.out.println((completedTaskNum + 1) + "th task is completed!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please enter a valid task number");
        }
    }

    private void deleteTask() {
        System.out.println("Which Task do you wanna delete?");
        String taskToDelete = scanner.nextLine();
        try {
            int taskToBeDelete = Integer.parseInt(taskToDelete);
            Task removedTask = tasks.remove(taskToBeDelete - 1);
            System.out.println("Deleted Task: " + removedTask.getTitle());
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Please enter a valid task number");
        } catch(NumberFormatException e) {
            System.out.println("Please enter a valid number");
        }
    }

    private void editTask() {
        try {
            System.out.println("Which Task do you wanna edit?");
            String taskToEditStr = scanner.nextLine();
            int taskToEditInt = Integer.parseInt(taskToEditStr);
            System.out.println("Current Title Name for " + taskToEditInt + "th task: " + tasks.get(taskToEditInt - 1).getTitle());
            System.out.println("Enter the New Title Name: ");
            String newTitle = scanner.nextLine();
            tasks.get(taskToEditInt - 1).editTitle(newTitle);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please enter a valid task number");
        }
    }

    private void run() {
        while (true) {
            System.out.println("\n====Todo App====");
            System.out.println("1. Add Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Complete Task");
            System.out.println("4. Delete Task");
            System.out.println("5. Edit A Task");
            System.out.println("6. Exit");

            System.out.println("Choose: ");
            String choice = scanner.nextLine();
            
            if (choice.equals("1")) {
                addTask();
            } else if (choice.equals("2")) {
                listTasks();
            } else if (choice.equals("3")) {
                completeTask();
            } else if (choice.equals("4")) {
                if (tasks.isEmpty()) {
                    System.out.println("There are no tasks");
                    continue;
                }
                deleteTask();
            } else if (choice.equals("5")) {
                if (tasks.isEmpty()) {
                    System.out.println("There are no tasks for you to edit");
                    continue;
                }
                editTask();
            } else if (choice.equals("6")) {
                System.out.println("You have selected Exit. Good Bye!");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
        scanner.close();
    }
    public static void main(String[] args) {
        TodoApp app = new TodoApp();
        app.run();
    }
}
