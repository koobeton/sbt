package ru.sbt.spring.proxy.persist;

import ru.sbt.spring.proxy.CacheOptions;
import ru.sbt.spring.utils.Utils;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilePersistStrategy implements PersistStrategy {

    @Override
    public boolean isCached(String key) {
        return Files.exists(Paths.get(key));
    }

    @Override
    public void persist(Object result, CacheOptions options) {
        Utils.serialize((Serializable) result, options.key(), options.isZip());
    }

    @Override
    public Object restore(CacheOptions options) {
        return Utils.deserialize(options.key(), options.isZip());
    }
}
