package lesson5.terminal.exception;

public class TerminalException extends RuntimeException {

    public TerminalException(String message) {
        super(message);
    }

    public TerminalException(String message, Throwable cause) {
        super(message, cause);
    }
}
