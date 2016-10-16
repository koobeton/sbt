package ru.sbt.jdbc.util;

import java.util.List;

public class Selector {

    public static <T> T selectOne(List<T> list) {
        return list.isEmpty() ? null : list.get(0);
    }
}
