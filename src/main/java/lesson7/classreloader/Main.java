package lesson7.classreloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String... args) {

        while (true) {
            System.out.println("Press any key to continue or enter exit or quit for exit");
            switch (readInput()) {
                case "exit":
                case "quit":
                    System.exit(0);
                    break;
            }
        }
    }

    private static String readInput() {
        try {
            return INPUT.readLine();
        } catch (IOException e) {
            throw new RuntimeException("I/O exception: " + e.getMessage(), e);
        }
    }
}
