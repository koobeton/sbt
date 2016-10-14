package ru.sbt.jdbc;

import org.junit.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class DBServiceTest {

    private static final String URL = "jdbc:h2:mem:lesson21";
    private static DBService dbService;

    @Before
    public void setUp() throws Exception {
        dbService = new DBServiceImpl(URL);
    }

    @After
    public void tearDown() throws Exception {
        dbService.shutdown();
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
    }

    @Test
    public void createTables() throws Exception {

        dbService.createTables();

        DatabaseMetaData metaData = dbService.getConnection().getMetaData();

        ResultSet rs = metaData.getTables(null, null, "Students", null);
        while (rs.next()) {
            assertEquals("Students", rs.getString("TABLE_NAME"));
        }

        rs = metaData.getTables(null, null, "Lessons", null);
        while (rs.next()) {
            assertEquals("Lessons", rs.getString("TABLE_NAME"));
        }

        rs = metaData.getTables(null, null, "Student_visits", null);
        while (rs.next()) {
            assertEquals("Student_visits", rs.getString("TABLE_NAME"));
        }
    }
}