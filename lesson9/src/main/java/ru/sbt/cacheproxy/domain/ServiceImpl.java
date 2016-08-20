package ru.sbt.cacheproxy.domain;

import ru.sbt.cacheproxy.proxy.annotation.Ignore;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    private static final int DEFAULT_LIST_SIZE = 1_000_000;

    private List<String> doHardWork(Object... objects) {
        String result = Arrays.stream(objects)
                .map(Object::toString)
                .collect(Collectors.joining());
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            throw new RuntimeException("Delay was interrupted", e);
        }
        return Collections.nCopies(DEFAULT_LIST_SIZE, result);
    }

    @Override
    public List<String> uncached(String item, double value, Date date) {
        return doHardWork(item, value, date);
    }

    @Override
    public List<String> cachedDefault(String item, double value, Date date) {
        return doHardWork(item, value, date);
    }

    @Override
    public List<String> cachedDefault() {
        return doHardWork("", 0, new Date());
    }

    @Override
    public List<String> limitedListSize(String item, double value, Date date) {
        return doHardWork(item, value, date);
    }

    @Override
    public List<String> bigLimitListSize(String item, double value, Date date) {
        return doHardWork(item, value, date);
    }

    @Override
    public List<String> ignoreArgs(String item, double value, Date date) {
        return doHardWork(item, value, date);
    }

    @Override
    public List<String> inMemory(String item, double value, Date date) {
        return doHardWork(item, value, date);
    }

    @Override
    public List<String> defaultFile(String item, double value, Date date) {
        return doHardWork(item, value, date);
    }

    @Override
    public List<String> explicitFileName(String item, double value, Date date) {
        return doHardWork(item, value, date);
    }
}
