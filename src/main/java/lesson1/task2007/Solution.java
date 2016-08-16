package lesson1.task2007;

import java.util.Scanner;

public class Solution {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        long n = scanner.nextLong();

        System.out.println(Long.numberOfTrailingZeros(n));
    }
}

