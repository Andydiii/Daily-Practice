package TodoList; // this means this class belongs to the package TodoList
import java.util.HashSet; // Import the HashMap class

public class ContainsDuplicate {
    public static boolean containsDuplicate(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            // seen.add(nums[i]) returns true if first time add, false when the number already existed in set.
            if (!seen.add(nums[i])) {
                return true;
            }
            
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums1 = {1,2,3,1};
        int[] nums2 = {2,3,4,5};
        System.out.println(containsDuplicate(nums1));
        System.out.println(containsDuplicate(nums2));
    }
}

