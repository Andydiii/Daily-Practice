import java.util.Scanner;

public class twoPointer {
    public static boolean isPalindrome(String s) {
        int ptr1 = 0;
        int ptr2 = s.length() - 1;
        while (ptr1 < ptr2) {
            if (s.charAt(ptr1) != s.charAt(ptr2)) {
                return false;
            }
            ptr1++;
            ptr2--;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String a = scanner.nextLine();
        System.out.println(isPalindrome(a));
    }
}