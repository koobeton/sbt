package ru.sbt.cacheproxy.utils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Utils {

    public static void serialize(Serializable o, String file, boolean zip) {
        try {
            if (zip) {
                serializeToZip(o, file);
            } else {
                serializeToFile(o, file);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file, e);
        } catch (IOException e) {
            throw new RuntimeException("I/O exception during serialization to file "
                    + file, e);
        }
    }

    private static void serializeToFile(Serializable o, String file) throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(file)))) {
            stream.writeObject(o);
        }
    }

    private static void serializeToZip(Serializable o, String zipFile) throws IOException {
        try (ObjectOutputStream stream = new ObjectOutputStream(
                new GZIPOutputStream(
                        new BufferedOutputStream(
                                new FileOutputStream(zipFile))))) {
            stream.writeObject(o);
        }
    }

    public static <T> T deserialize(String file, boolean zip) {
        try {
            return zip ? deserializeFromZip(file) : deserializeFromFile(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + file, e);
        } catch (IOException e) {
            throw new RuntimeException("I/O exception during deserialization from file " + file, e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found in serialized file "
                    + file + " : " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T deserializeFromFile(String file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(
                new BufferedInputStream(
                        new FileInputStream(file)))) {
            return (T) stream.readObject();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T deserializeFromZip(String zipFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream stream = new ObjectInputStream(
                new GZIPInputStream(
                        new BufferedInputStream(
                                new FileInputStream(zipFile))))) {
            return (T) stream.readObject();
        }
    }
}
