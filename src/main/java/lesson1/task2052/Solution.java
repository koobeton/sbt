package lesson1.task2052;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int k = scanner.nextInt();

        List<Integer> a = new ArrayList<>(n);
        int[] b = new int[k];

        for (int i = 0; i < n; i++) {
            a.add(scanner.nextInt());
        }

        for (int i = 0; i < k; i++) {
            b[i] = scanner.nextInt();
        }

        List<Integer> result = null;
        for (int bi : b) {
            result = new ArrayList<>();
            for (int i = 0; i < a.size(); i++) {
                if ((i + 1) % bi != 0) {
                    result.add(a.get(i));
                }
            }
            a = result;
        }

        for (int e : result) {
            System.out.print(e + " ");
        }
    }
}
