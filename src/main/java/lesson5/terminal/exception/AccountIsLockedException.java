package lesson5.terminal.exception;

public class AccountIsLockedException extends TerminalException {

    private static final String MESSAGE = "Your account has been locked because " +
            "an incorrect PIN was entered 3 times. You can try again after %d seconds.";

    public AccountIsLockedException(long unlockAfter) {
        super(String.format(MESSAGE, unlockAfter));
    }

    public AccountIsLockedException(long unlockAfter, Throwable cause) {
        super(String.format(MESSAGE, unlockAfter), cause);
    }
}
