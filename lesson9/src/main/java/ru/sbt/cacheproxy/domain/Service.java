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
}
