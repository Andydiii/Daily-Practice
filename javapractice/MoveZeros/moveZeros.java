import java.util.Arrays;
/**
 * moveZeros
 */
public class moveZeros {
    public static void main(String[] args) {
        int[] array = {0, 1, 0, 3, 12};
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            if (array[left] == 0 && array[right] != 0) {
                int temp = array[right];
                array[right] = array[left];
                array[left] = temp;
                left++;
                right--;
            } else if (array[left] == 0 && array[right] == 0) {
                right--;
            } else if (array[left] != 0 && array[right] == 0) {
                left++;
                right--;
            } else {
                left++;
            }
        }
        System.out.println(Arrays.toString(array));
    }
}