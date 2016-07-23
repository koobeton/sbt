package lesson1.task2036;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = scanner.next();
        }

        for (String word : words) {
            if (!Pattern.matches("[a-z]*[eyuioa]{3,}[a-z]*", word)) {
                System.out.println(word);
            }
        }
    }
}
