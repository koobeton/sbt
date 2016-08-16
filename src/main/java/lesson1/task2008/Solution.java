package lesson1.task2008;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int w = scanner.nextInt();

        int summaryWeight = 0;
        int itemsCount = 0;

        for (int i = 0; i < n; i++) {
            int a = scanner.nextInt();
            if (summaryWeight + a <= w) {
                summaryWeight += a;
                itemsCount++;
            }
        }

        System.out.println(itemsCount + " " + summaryWeight);
    }
}
