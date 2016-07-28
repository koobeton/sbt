package lesson1.task2056;

import java.util.*;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) {
            words.add(scanner.next().toLowerCase());
        }

        Map<String, Integer> map = new HashMap<>();

        for (String word : words) {
            if (map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1);
            }
        }

        int max = 0;
        Set<String> maxWords = new TreeSet<>();

        for (String word : map.keySet()) {
            if (map.get(word) == max) {
                maxWords.add(word);
            } else if (map.get(word) > max) {
                max = map.get(word);
                maxWords.clear();
                maxWords.add(word);
            }
        }

        for (String word : maxWords) {
            System.out.println(word);
        }
    }
}
