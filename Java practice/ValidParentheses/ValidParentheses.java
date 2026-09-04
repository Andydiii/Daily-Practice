package ValidParentheses;

import java.util.ArrayDeque;
import java.util.Deque;

public class ValidParentheses {
    public static boolean isValid(String s) {
        Deque<Character> Stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                Stack.push(s.charAt(i));
            } else if ((s.charAt(i) == ')' || s.charAt(i) == '}' || s.charAt(i) == ']')  && Stack.isEmpty()) {
                return false;
            } else if (s.charAt(i) == ')' && Stack.peek() == '(') {
                Stack.pop();
            } else if (s.charAt(i) == '}' && Stack.peek() == '{') {
                Stack.pop();
            } else if (s.charAt(i) == ']' && Stack.peek() == '[') {
                Stack.pop();
            }
        }
        if (Stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(isValid("()"));      // true
        System.out.println(isValid("()[]{}"));  // true
        System.out.println(isValid("(]"));      // false
        System.out.println(isValid("([)]"));    // false
        System.out.println(isValid("{[]}"));    // true
        System.out.println(isValid(""));        // true
        System.out.println(isValid("("));       // false
        System.out.println(isValid("]"));       // false
    }
}

