package ru.sbt.cacheproxy.proxy.persist;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static ru.sbt.cacheproxy.proxy.annotation.Cache.Type.*;

public class PersistStrategiesContextTest {

    private PersistStrategiesContext context;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        context = new PersistStrategiesContext();
    }

    @Test
    public void registerPersistStrategy() throws Exception {

        context.registerPersistStrategy(IN_MEMORY, new InMemoryPersistStrategy());
        context.registerPersistStrategy(FILE, new FilePersistStrategy());

        assertEquals(InMemoryPersistStrategy.class, context.getPersistStrategy(IN_MEMORY).getClass());
        assertEquals(FilePersistStrategy.class, context.getPersistStrategy(FILE).getClass());
    }

    @Test
    public void removePersistStrategy() throws Exception {

        context.registerPersistStrategy(IN_MEMORY, new InMemoryPersistStrategy());
        context.registerPersistStrategy(FILE, new FilePersistStrategy());
        context.removePersistStrategy(IN_MEMORY);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(IN_MEMORY.toString());

        context.getPersistStrategy(IN_MEMORY);
    }
}