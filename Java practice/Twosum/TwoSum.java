import java.util.Arrays;
/*
Input:
nums = [2, 7, 11, 15]
target = 9

Output:
[0, 1]
*/

public class TwoSum {

    // twoSum here can be private method since only main method is using it. 
    // it has to be statis since main method can call it directly without creating TwoSum object.
    public static int[] twoSum(int[] nums, int target) {
        // 这里放你自己的实现
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            } 
        }
        return new int[]{};
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        int[] result = twoSum(nums, target);

        System.out.println(Arrays.toString(result));
    }
}
