package lesson1.task2057;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        NavigableMap<Integer, Integer> map = new TreeMap<>();

        for (int i = 0; i < n; i++) {
            int op = scanner.nextInt();
            switch (op) {
                case 1:
                    int x = scanner.nextInt();
                    Integer v = map.get(x);
                    map.put(x, v == null ? 1 : v + 1);
                    break;
                case 2:
                    Map.Entry<Integer, Integer> min = map.firstEntry();
                    int minKey = min.getKey();
                    int minValue = min.getValue();
                    if (minValue == 1) {
                        map.pollFirstEntry();
                    } else {
                        map.replace(minKey, --minValue);
                    }
                    System.out.println(minKey);
                    break;
            }
        }
    }
}
