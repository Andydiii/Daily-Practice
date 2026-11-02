import java.util.*;

public class Anagrams {
    public static List<List<String>> getAnagrams(List<String> strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            if (map.containsKey(key)) {
                map.get(key).add(str);
            } else {
                map.put(key, new ArrayList<>(List.of(str)));
            }
        }
        List<List<String>> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> strs = new ArrayList<>(List.of("ate", "eat", "aet", "abc", "bca", "eaff"));
        System.out.println(getAnagrams(strs)); 
    }
}

// time: O(n * klogk) where n is the size of the strs and k is the max size of the word. klogk is the sort time complexity
// space: O(n * k)