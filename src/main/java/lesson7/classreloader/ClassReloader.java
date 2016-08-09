package lesson7.classreloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassReloader extends ClassLoader {

    private final String className;
    private final String pathToClassFile;

    public ClassReloader(ClassLoader parent, String className, String pathToClassFile) {
        super(parent);
        this.className = className;
        this.pathToClassFile = pathToClassFile;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (!className.equals(name)) return super.loadClass(name);

        byte[] classData;
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(pathToClassFile));
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            int data;
            while ((data = input.read()) != -1) {
                output.write(data);
            }
            classData = output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("I/O exception: " + e.getMessage(), e);
        }

        return defineClass(name, classData, 0, classData.length);
    }
}
