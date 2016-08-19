package ru.sbt.cacheproxy.domain;

import ru.sbt.cacheproxy.proxy.annotation.Cache;

import java.util.Date;
import java.util.List;

public interface Service {

    List<String> uncached(String item, double value, Date date);

    @Cache
    List<String> cachedDefault(String item, double value, Date date);

    @Cache
    List<String> cachedDefault();

    @Cache(maxListSize = 100_000)
    List<String> limitedListSize(String item, double value, Date date);

    @Cache(maxListSize = 10_000_000)
    List<String> bigLimitListSize(String item, double value, Date date);
}
