package lesson7.classreloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final String CLASS_NAME = "lesson7.classreloader.FlipFlop";
    private static final String PATH_TO_CLASS_FILE = "./target/classes/lesson7/classreloader/FlipFlop.class";

    private static final BufferedReader INPUT = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String... args) throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {

        ClassLoader parentClassLoader = ClassReloader.class.getClassLoader();

        ClassReloader reloader = new ClassReloader(parentClassLoader, CLASS_NAME, PATH_TO_CLASS_FILE);
        Class clazz = reloader.loadClass(CLASS_NAME);

        Reloadable object = (Reloadable) clazz.newInstance();
        object.doSomething();

        pause();

        //create new class loader so classes can be reloaded.
        reloader = new ClassReloader(parentClassLoader, CLASS_NAME, PATH_TO_CLASS_FILE);
        clazz = reloader.loadClass(CLASS_NAME);

        object = (Reloadable) clazz.newInstance();
        object.doSomething();
    }

    private static void pause() {
        while (true) {
            System.out.println("Enter 'r' for reloading class or 'q' for exit");
            switch (readInput()) {
                case "r":
                    return;
                case "q":
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
