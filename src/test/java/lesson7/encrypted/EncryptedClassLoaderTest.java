package lesson7.encrypted;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class EncryptedClassLoaderTest {

    private static final String ENCRYPTED_DIR = "./cipher/";
    private static final String ENCRYPTED_CLASS = "lesson7.encrypted.EncryptedClass";
    private static final String KEY = "SecretKey";

    private EncryptedClassLoader loader;

    @Before
    public void setUp() throws Exception {
        loader = new EncryptedClassLoader(
                KEY,
                new File(ENCRYPTED_DIR),
                EncryptedClassLoader.class.getClassLoader());
    }

    @Test
    public void findClass() throws Exception {

        File encryptedFile = new File(ENCRYPTED_DIR,
                ENCRYPTED_CLASS.replaceAll("\\.", "/").concat(".class"));
        assertTrue(encryptedFile.exists());

        IEncrypted decryptedObject = (IEncrypted) loader.findClass(ENCRYPTED_CLASS).newInstance();
        assertEquals(loader, decryptedObject.getClass().getClassLoader());
        assertEquals("This class has been encrypted and then has been decrypted.",
                decryptedObject.whatHappened());
    }
}