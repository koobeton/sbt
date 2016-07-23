package lesson1.task2003;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int sum = 0;
        boolean sign = false;
        for (int i = 0; i < n; i++) {
            int next = scanner.nextInt();
            sum += (sign = !sign) ? next : -next;
        }

        System.out.println(sum);
    }
}
