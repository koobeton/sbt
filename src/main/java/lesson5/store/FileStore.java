package lesson5.store;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

public class FileStore implements Store {

    private final File file;

    public FileStore(File file) {
        this.file = file;
    }

    @Override
    public void save(String t) {

        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            out.println(t);
        } catch (IOException e) {
            throw new StoreException("Unable to write to file " + file.getName(), e);
        }
    }

    @Override
    public List<String> getAll() {

        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new StoreException("Unable to read from file " + file.getName(), e);
        }
    }
}
