package lesson5.terminal;

import lesson5.terminal.exception.AccountIsLockedException;
import lesson5.terminal.exception.InvalidPinException;
import lesson5.terminal.exception.TerminalException;

public class TerminalImpl implements Terminal {

    private static final int DEFAULT_PIN_ATTEMPTS = 3;
    private static final int MAX_LOCK_DURATION = 5;

    private final TerminalServer server;
    private final PinValidator pinValidator;

    private boolean pinAccepted = false;
    private int remainingPinAttempts = DEFAULT_PIN_ATTEMPTS;
    private long accountLockTime;

    public TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
    }

    @Override
    public int checkAccount() {
        checkPinAccepted();
        return server.checkAccount();
    }

    @Override
    public int getMoney(int amount) {
        checkPinAccepted();
        checkMultipleOf100(amount);
        return server.get(amount);
    }

    @Override
    public int putMoney(int amount) {
        checkPinAccepted();
        checkMultipleOf100(amount);
        return server.put(amount);
    }

    public void enterPin(int pin) {
        isAccountUnlocked();
        pinAccepted = pinValidator.validate(pin);
        if (pinAccepted) {
            remainingPinAttempts = DEFAULT_PIN_ATTEMPTS;
        } else if (--remainingPinAttempts > 0) {
            throw new InvalidPinException(remainingPinAttempts);
        } else {
            lockAccount();
        }
    }

    private void checkPinAccepted() {
        isAccountUnlocked();
        if (!pinAccepted) throw new TerminalException("You must first enter the PIN.");
    }

    private boolean isAccountUnlocked() {
        long lockDuration = (System.currentTimeMillis() - accountLockTime) / 1000;
        if (lockDuration >= MAX_LOCK_DURATION) {
            return true;
        } else {
            throw new AccountIsLockedException(MAX_LOCK_DURATION - lockDuration);
        }
    }

    private void lockAccount() {
        accountLockTime = System.currentTimeMillis();
    }

    private void checkMultipleOf100(int amount) {
        if (amount % 100 != 0) throw new TerminalException("You can put and get money only " +
                "if the amount is a multiple of 100.");
    }
}
