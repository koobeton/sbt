package lesson1.task2021;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }

        for (int step = 0; step < 2; step++) {
            int max = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] > max) max = a[i];
            }
            for (int i = 0; i < n; i++) {
                if (a[i] == max) a[i] /= 2;
            }
        }

        for (int e : a) {
            System.out.println(e + " ");
        }
    }
}
