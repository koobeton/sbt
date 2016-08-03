package lesson5.store;

import java.sql.SQLException;
import java.util.List;

public class DataBaseStore implements Store {

    private final Db db;

    public DataBaseStore(Db db) {
        this.db = db;
    }

    @Override
    public void save(String t) {
        try {
            db.insert(t);
        } catch (SQLException e) {
            throw new DbException("Unable to insert data to database", e);
        } finally {
            db.close();
        }
    }

    @Override
    public List<String> getAll() {
        List<String> strings = null;
        try {
            strings = db.selectAll();
        } catch (SQLException e) {
            throw new DbException("Unable to select data from database", e);
        } finally {
            db.close();
        }
        return strings;
    }
}
