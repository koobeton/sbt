package ru.sbt.cacheproxy.domain;

import ru.sbt.cacheproxy.proxy.annotation.Cache;
import ru.sbt.cacheproxy.proxy.annotation.Ignore;

import java.util.Date;
import java.util.List;

import static ru.sbt.cacheproxy.proxy.annotation.Cache.Type.*;

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

    @Cache
    List<String> ignoreArgs(@Ignore String item, double value, @Ignore Date date);

    @Cache(cacheType = IN_MEMORY)
    List<String> inMemory(String item, double value, Date date);
}
