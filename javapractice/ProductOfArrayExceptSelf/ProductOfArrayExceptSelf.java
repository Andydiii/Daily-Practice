public class ProductOfArrayExceptSelf {
    public static int[] solution(int[] nums) {
        int size = nums.length;
        int[] prefixProduct = new int[size];
        int[] postProduct = new int[size];
        prefixProduct[0] = nums[0];
        postProduct[size - 1] = nums[size - 1];
        
        for (int i = 1; i < nums.length; i++) {
            prefixProduct[i] = prefixProduct[i - 1] * nums[i];
        }
        for (int j = size - 2; j > -1; j--) {
            postProduct[j] = postProduct[j + 1] * nums[j];
        }

        for (int i = 0; i < size; i++) {
            if (i == 0) {
                nums[i] = postProduct[i + 1];
            } else if (i == size - 1) {
                nums[i] = prefixProduct[i - 1];
            } else {
                nums[i] = prefixProduct[i - 1] * postProduct[i + 1];
            }
        }

        return nums;
    }

    public static void main(String[] args) {
        
    }
}