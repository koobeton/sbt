package ru.sbt.cacheproxy.domain;

import org.junit.Before;
import org.junit.Test;
import ru.sbt.cacheproxy.proxy.CacheProxy;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ServiceTest {

    private CacheProxy cacheProxy;
    private Service service;

    @Before
    public void setUp() throws Exception {
        cacheProxy = new CacheProxy("./");
        service = cacheProxy.cache(new ServiceImpl());
    }

    @Test
    public void uncached() throws Exception {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.uncached("Test 1", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.uncached("Test 1", 200, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.uncached("Test 1", 100, new Date(start1));
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("Test 1"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("Test 1"));
        assertTrue(duration2 > 2000);

        assertEquals(1_000_000, result3.size());
        assertTrue(result3.get(0).startsWith("Test 1"));
        assertTrue(duration3 > 2000);
    }

    @Test
    public void cachedDefault() throws Exception {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.cachedDefault("Test 2", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.cachedDefault("Test 2", 200, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.cachedDefault("Test 2", 100, new Date(start1));
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("Test 2"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("Test 2"));
        assertTrue(duration2 > 2000);

        assertEquals(1_000_000, result3.size());
        assertTrue(result3.get(0).startsWith("Test 2"));
        assertTrue(duration3 < 2000);
    }

    @Test
    public void cachedDefaultNoArgs() {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.cachedDefault();
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.cachedDefault();
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.cachedDefault();
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("0"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("0"));
        assertTrue(duration2 < 2000);

        assertEquals(1_000_000, result3.size());
        assertTrue(result3.get(0).startsWith("0"));
        assertTrue(duration3 < 2000);
    }
}