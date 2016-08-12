package lesson7.encrypted;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BinaryOperator;

public class CipherHelper {

    private static final String RAW = "./target/classes/lesson7/encrypted/EncryptedClass.class";
    private static final String ENCRYPTED = "./cipher/lesson7/encrypted/EncryptedClass.class";
    private static final String KEY = "SecretKey";

    public static void main(String... args) throws IOException {

        Files.write(Paths.get(ENCRYPTED), encryptFile(KEY, Paths.get(RAW)));
        //Files.write(Paths.get(ENCRYPTED), decryptFile(KEY, Paths.get(ENCRYPTED)));
    }

    public static byte[] encryptFile(String key, Path file) {
        return cipher(key, file, (b, k) -> (byte) (b + k));
    }

    public static byte[] decryptFile(String key, Path file) {
        return cipher(key, file, (b, k) -> (byte) (b - k));
    }

    private static byte[] cipher(String key, Path file, BinaryOperator<Byte> operator) {
        try {
            int hash = key.hashCode();
            byte[] data = Files.readAllBytes(file);
            for (int i = 0; i < data.length; i++) {
                data[i] = operator.apply(data[i], (byte) hash);
            }
            return data;
        } catch (IOException e) {
            throw new RuntimeException("I/O Exception during cipher: "
                    + e.getMessage(), e);
        }
    }
}
