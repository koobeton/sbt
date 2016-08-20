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
        cacheProxy = new CacheProxy("./src/test/resources/");
        service = cacheProxy.cache(new ServiceImpl());
    }

    @Test
    public void uncached() throws Exception {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.uncached("Uncached", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.uncached("Uncached", 200, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.uncached("Uncached", 100, new Date(start1));
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("Uncached"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("Uncached"));
        assertTrue(duration2 > 2000);

        assertEquals(1_000_000, result3.size());
        assertTrue(result3.get(0).startsWith("Uncached"));
        assertTrue(duration3 > 2000);
    }

    @Test
    public void cachedDefault() throws Exception {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.cachedDefault("Cached default", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.cachedDefault("Cached default", 200, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.cachedDefault("Cached default", 100, new Date(start1));
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("Cached default"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("Cached default"));
        assertTrue(duration2 > 2000);

        assertEquals(1_000_000, result3.size());
        assertTrue(result3.get(0).startsWith("Cached default"));
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

    @Test
    public void limitedListSize() throws Exception {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.limitedListSize("Limited list size", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.limitedListSize("Limited list size", 200, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.limitedListSize("Limited list size", 100, new Date(start1));
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(100_000, result1.size());
        assertTrue(result1.get(0).startsWith("Limited list size"));
        assertTrue(duration1 > 2000);

        assertEquals(100_000, result2.size());
        assertTrue(result2.get(0).startsWith("Limited list size"));
        assertTrue(duration2 > 2000);

        assertEquals(100_000, result3.size());
        assertTrue(result3.get(0).startsWith("Limited list size"));
        assertTrue(duration3 < 2000);
    }

    @Test
    public void bigLimitListSize() throws Exception {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.bigLimitListSize("Very big limit list size", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.bigLimitListSize("Very big limit list size", 200, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.bigLimitListSize("Very big limit list size", 100, new Date(start1));
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("Very big limit list size"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("Very big limit list size"));
        assertTrue(duration2 > 2000);

        assertEquals(1_000_000, result3.size());
        assertTrue(result3.get(0).startsWith("Very big limit list size"));
        assertTrue(duration3 < 2000);
    }

    @Test
    public void ignoreArgs() throws Exception {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.ignoreArgs("First arg is ignored", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.ignoreArgs("Second arg is significant", 200, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.ignoreArgs("Third arg is ignored", 100, new Date(start3));
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("First arg is ignored"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("Second arg is significant"));
        assertTrue(duration2 > 2000);

        assertEquals(1_000_000, result3.size());
        assertTrue(result3.get(0).startsWith("First arg is ignored"));
        assertTrue(duration3 < 2000);
    }

    @Test
    public void inMemory() throws Exception {

        long start1 = System.currentTimeMillis();
        List<String> result1 = service.inMemory("In memory", 100, new Date(start1));
        long duration1 = System.currentTimeMillis() - start1;

        long start2 = System.currentTimeMillis();
        List<String> result2 = service.inMemory("In memory", 200, new Date(start2));
        long duration2 = System.currentTimeMillis() - start2;

        long start3 = System.currentTimeMillis();
        List<String> result3 = service.inMemory("In memory", 100, new Date(start1));
        long duration3 = System.currentTimeMillis() - start3;

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("In memory"));
        assertTrue(duration1 > 2000);

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("In memory"));
        assertTrue(duration2 > 2000);

        assertEquals(1_000_000, result3.size());
        assertTrue(result3.get(0).startsWith("In memory"));
        assertTrue(duration3 < 2000);
    }

    @Test
    public void defaultFile() throws Exception {

        List<String> result1 = service.defaultFile("Default file", 100, new Date());

        List<String> result2 = service.defaultFile("Default file", 200, new Date());

        List<String> result3 = service.defaultFile("Default file", 100, new Date());

        assertEquals(1_000_000, result1.size());
        assertTrue(result1.get(0).startsWith("Default file100"));

        assertEquals(1_000_000, result2.size());
        assertTrue(result2.get(0).startsWith("Default file200"));

        assertEquals(1_000_000, result3.size());
        assertEquals(result1.get(0), result3.get(0));
    }
}