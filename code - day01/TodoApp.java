import java.util.ArrayList;
import java.util.Scanner;

public class TodoApp {
    public static void main(String[] args) {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n====Todo App====");
            System.out.println("1. Add Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Complete Task");
            System.out.println("4. Exit");


            System.out.println("Choose: ");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("Please enter the task title: ");
                String title = scanner.nextLine();
                Task task = new Task(title);
                tasks.add(task);
                System.out.println("You have added a task successfully!");
            } else if (choice.equals("2")) {
                System.out.println("You have selected List Tasks");
                String status;
                for (int i = 0; i < tasks.size(); i++) {
                    Task currTask = tasks.get(i);
                    status = currTask.completed ? "[x]" : "[ ]";
                    System.out.println((i + 1) + ". " + status + " " + currTask.title);
                }
            } else if (choice.equals("3")) {
                System.out.println("Which Task did you complete: ");
                String completedTaskStr = scanner.nextLine();
                int completedTaskNum = Integer.parseInt(completedTaskStr) - 1;
                tasks.get(completedTaskNum).completed = true;
                System.out.println((completedTaskNum + 1) + "th task is completed!");
            } else if (choice.equals("4")){
                System.out.println("You have selected Exit. Good Bye!");
            } else {
                System.out.println("Invalid choice");
                break;
            }
        }

        scanner.close();
    }
}
