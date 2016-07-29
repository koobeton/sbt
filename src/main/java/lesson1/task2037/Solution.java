package lesson1.task2037;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    private static List<String> words = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String text = scanner.next();
        int k = scanner.nextInt();

        String word = "";
        for (char c : text.toCharArray()) {
            if (c == ',') {
                append(word, k);
                word = "";
            } else {
                word += Character.toString(c);
            }
        }
        append(word, k);

        for (String w : words) {
            System.out.print(w);
        }
    }

    private static void append(String word, int k) {
        if (word.length() >= k) {
            words.add(words.isEmpty() ? word : "," + word);
        }
    }
}
