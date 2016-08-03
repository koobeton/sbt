package lesson5.store;

public class DbException extends StoreException {

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }
}
