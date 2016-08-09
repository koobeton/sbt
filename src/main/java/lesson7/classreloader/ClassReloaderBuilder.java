package lesson7.classreloader;

public class ClassReloaderBuilder {

    private final String className;
    private final String pathToClassFile;
    private final ClassLoader parentClassLoader;

    public ClassReloaderBuilder(String className, String pathToClassFile) {
        this.className = className;
        this.pathToClassFile = pathToClassFile;
        this.parentClassLoader = ClassReloader.class.getClassLoader();
    }

    public Object newReloadedClassInstance() throws
            ClassNotFoundException, IllegalAccessException, InstantiationException {
        return new ClassReloader(parentClassLoader, className, pathToClassFile)
                .loadClass(className)
                .newInstance();
    }
}
