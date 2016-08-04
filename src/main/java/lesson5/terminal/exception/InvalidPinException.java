package lesson5.terminal.exception;

public class InvalidPinException extends TerminalException {

    private static final String MESSAGE = "The entered PIN is incorrect. " +
            "You have %d attempts before locking the account for 5 seconds.";

    public InvalidPinException(int remainingAttempts) {
        super(String.format(MESSAGE, remainingAttempts));
    }

    public InvalidPinException(int remainingAttempts, Throwable cause) {
        super(String.format(MESSAGE, remainingAttempts), cause);
    }
}
