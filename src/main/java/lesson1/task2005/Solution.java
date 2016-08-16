package lesson1.task2005;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int index = 1;
        int min = 10000;
        for (int i = 0; i < n; i++) {
            int a = scanner.nextInt();
            if (a < min) {
                min = a;
                index = i + 1;
            }
        }

        System.out.println(index);
    }
}
