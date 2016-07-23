package lesson1.task2037;

import java.util.Scanner;

/**
 * Result - 33/35
 * */
public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String text = scanner.next();
        int k = scanner.nextInt();

        String[] words = text.split(",");

        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (word.length() >= k) {
                if (sb.length() != 0) sb.append(",");
                sb.append(word);
            }
        }

        System.out.println(sb);
    }
}
