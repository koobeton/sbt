package lesson5.store;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileStoreTest {

    private final List<String> strings = Arrays.asList("first string", "second string", "third string");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void saveAndGetAll() throws Exception {
        File file = new File("file.txt");
        Store fileStore = new FileStore(file);

        strings.forEach(fileStore::save);
        assertTrue(file.exists());

        List<String> readFromFile = fileStore.getAll();
        assertTrue(readFromFile.containsAll(strings));

        assertTrue(file.delete());
    }

    @Test
    public void saveThrowsException() throws Exception {
        thrown.expect(StoreException.class);
        thrown.expectMessage("Unable to write to file");

        File file = new File("");
        Store fileStore = new FileStore(file);
        fileStore.save("must throw an exception");
    }

    @Test
    public void getAllThrowsException() throws Exception {
        thrown.expect(StoreException.class);
        thrown.expectMessage("Unable to read from file");

        File file = new File("");
        Store fileStore = new FileStore(file);
        fileStore.getAll();
    }
}