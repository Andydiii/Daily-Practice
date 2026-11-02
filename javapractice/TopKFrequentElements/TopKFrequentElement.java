import java.util.*;

public class TopKFrequentElement {
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
            } else {
                map.put(num, map.get(num) + 1);
            }
        }
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(map.entrySet());
        entries.sort((a, b) -> {
            return Integer.compare(b.getValue(), a.getValue()); // des order if b is before a.
        });
        /*
            .map(entry -> entry.getKey())       // Stream<Integer>
            .mapToInt(entry -> entry.getKey())  // IntStream
        */ 

        return entries.subList(0, k).stream().mapToInt((entry) -> {
            return entry.getKey();
        }).toArray();
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1, 1, 1, 2, 2, 3};
        System.out.println(topKFrequent(nums, 2));
    }
}