package MostFrequentChar;
import java.util.HashMap;
import java.util.Map;
public class MostFrequentChar {
    /*
        "banana" -> 'a'
        "apple" -> 'p'
    */
    private static Character mostFrequentChar(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        char mostFrequentChar = s.charAt(0);
        int maxCount = map.get(s.charAt(0));
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequentChar = entry.getKey();
            }
        }
        return mostFrequentChar;
    }
    public static void main(String args[]) {
        String s = "abcdefgABCDa-'.]eq";
        System.out.println("most frequent char is: " + mostFrequentChar(s));
    }
}
