package lesson1.task2004;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int y = scanner.nextInt();
        int leapYear = y % 400 == 0 || (y % 4 == 0 && y % 100 != 0) ? 1 : 0;

        System.out.println(leapYear);
    }
}
