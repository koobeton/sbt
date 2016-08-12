package lesson7.ru.sbt;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PluginManagerTest {

    private static final String COOL_PLUGIN = "lesson7.ru.sbt.plugins.plugin.CoolPlugin";
    private PluginManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new PluginManager("./plugins");
    }

    @Test
    public void load() throws Exception {

        Plugin plugin1 = manager.load("plugin1", COOL_PLUGIN);
        assertEquals("BrowserClass from Cool Plugin 1", plugin1.doUsefull());
        assertEquals(PluginClassLoader.class, plugin1.getClass().getClassLoader().getClass());

        Plugin plugin2 = manager.load("plugin2", COOL_PLUGIN);
        assertEquals("BrowserClass from Cool Plugin 2", plugin2.doUsefull());
        assertEquals(PluginClassLoader.class, plugin2.getClass().getClassLoader().getClass());

        assertNotEquals(plugin1.getClass().getClassLoader(), plugin2.getClass().getClassLoader());
    }
}