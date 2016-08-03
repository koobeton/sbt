package lesson5.store;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.sql.SQLException;
import java.util.List;

public class DataBaseStoreTest {

    private Store dbStore = new DataBaseStore(new Db() {
        @Override
        public void insert(String line) throws SQLException {
            throw new SQLException("Thrown from mock database");
        }

        @Override
        public List<String> selectAll() throws SQLException {
            throw new SQLException("Thrown from mock database");
        }

        @Override
        public void close() {
        }
    });

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        thrown.expect(StoreException.class);
    }

    @Test
    public void saveThrowsException() throws Exception {
        thrown.expectMessage("Unable to insert data to database");
        dbStore.save("must throw an exception");
    }

    @Test
    public void getAllThrowsException() throws Exception {
        thrown.expectMessage("Unable to select data from database");
        dbStore.getAll();
    }
}