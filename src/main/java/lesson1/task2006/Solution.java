package lesson1.task2006;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int inches = n / 3 + (n % 3 == 2 ? 1 : 0);

        System.out.println(inches / 12 + " " + (inches % 12));
    }
}
