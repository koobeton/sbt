package ru.sbt.spring.proxy.persist;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static ru.sbt.spring.proxy.annotation.Cache.Type.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ru.sbt.spring.config.TestConfig.class})
public class PersistStrategiesContextTest {

    @Autowired
    private PersistStrategiesContext context;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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