import java.util.Arrays;

/**
 * two-pointer
 */
public class twoPointer {
    public static void main(String[] args) {
        // char[] letters = {'h', 'e', 'l', 'l', 'o'};
        // char[] letters = {'a', 'b', 'c', 'd'};
        // char[] letters = {'a'};
        char[] letters = {};

        int left = 0;
        int right = letters.length - 1;

        while (left < right) {
            char temp = letters[left];
            letters[left] = letters[right];
            letters[right] = temp;
            left++;
            right--;
        }

        System.out.println("result array: " + Arrays.toString(letters));
    }
}