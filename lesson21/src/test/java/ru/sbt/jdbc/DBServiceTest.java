package ru.sbt.jdbc;

import org.junit.*;

import java.sql.Connection;

import static org.junit.Assert.*;

public class DBServiceTest {

    private static final String URL = "jdbc:h2:mem:lesson21";
    private static DBService dbService;

    @BeforeClass
    public static void createDBService() {
        dbService = new DBServiceImpl(URL);
    }

    @AfterClass
    public static void shutdownDBService() {
        dbService.shutdown();
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getConnection() throws Exception {

        Connection conn = dbService.getConnection();

        assertTrue(conn.isValid(0));
        assertEquals("H2", conn.getMetaData().getDatabaseProductName());
        assertEquals(URL, conn.getMetaData().getURL());
    }

    @Test
    public void shutdown() throws Exception {

        dbService.shutdown();
        assertTrue(dbService.getConnection().isClosed());

        dbService = new DBServiceImpl(URL);
    }
}