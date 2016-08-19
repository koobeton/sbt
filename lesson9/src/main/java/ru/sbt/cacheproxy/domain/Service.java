package ru.sbt.cacheproxy.domain;

import java.util.Date;
import java.util.List;

public interface Service {

    List<String> uncached(String item, double value, Date date);
}
