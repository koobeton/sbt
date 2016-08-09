package lesson7.classreloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final String CLASS_NAME = "lesson7.classreloader.Authorizer";
    private static final String PATH_TO_CLASS_FILE = "./target/classes/lesson7/classreloader/Authorizer.class";

    private static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String... args) throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {

        ClassReloaderBuilder builder = new ClassReloaderBuilder(CLASS_NAME, PATH_TO_CLASS_FILE);

        while (true) {
            System.out.println("Enter access code or 'exit' for exit");
            String accessCode = readInput();
            if (accessCode.equals("exit")) {
                return;
            } else {
                //create new class loader so classes can be reloaded.
                Reloadable object = (Reloadable) builder.newReloadedClassInstance();
                System.out.println(object.check(accessCode)
                        ? "+++ Access GRANTED +++"
                        : "--- Access DENIED ---");
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
