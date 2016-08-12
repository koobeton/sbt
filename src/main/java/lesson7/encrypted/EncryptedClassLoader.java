package lesson7.encrypted;

import java.io.File;

public class EncryptedClassLoader extends ClassLoader {

    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        File encryptedFile = new File(dir, name.replaceAll("\\.", "/").concat(".class"));
        if (!encryptedFile.exists()) {
            return super.findClass(name);
        }

        byte[] data = CipherHelper.decryptFile(key, encryptedFile.toPath());

        return defineClass(name, data, 0, data.length);
    }
}
