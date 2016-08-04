package lesson5.terminal.exception;

public class NotEnoughMoneyException extends TerminalException {

    private static final String MESSAGE = "Not enough money in the account.";

    public NotEnoughMoneyException() {
        super(MESSAGE);
    }

    public NotEnoughMoneyException(Throwable cause) {
        super(MESSAGE, cause);
    }
}
