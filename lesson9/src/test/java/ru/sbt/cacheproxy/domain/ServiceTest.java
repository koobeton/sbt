package ru.sbt.cacheproxy.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceTest {

    private Service service;

    @Before
    public void setUp() throws Exception {
        service = new ServiceImpl();
    }

    @Test
    public void uncached() throws Exception {
        long start1 = System.currentTimeMillis();
        List<String> result1 = service.uncached("Hard Work", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.uncached("Hard Work", 100, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("Hard Work"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("Hard Work"));
        assertTrue(duration2 > 2000);
    }
}