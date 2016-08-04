package lesson5.terminal;

import lesson5.terminal.exception.AccountIsLockedException;
import lesson5.terminal.exception.InvalidPinException;
import lesson5.terminal.exception.NotEnoughMoneyException;
import lesson5.terminal.exception.TerminalException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TerminalTest {

    private TerminalImpl terminal;
    private TerminalServer server;
    private PinValidator pinValidator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        terminal = new TerminalImpl(
                server = new TerminalServer(),
                pinValidator = new PinValidator(123456));
    }

    @Test
    public void pinAccepted() throws Exception {
        terminal.enterPin(123456);

        Field pinAccepted = TerminalImpl.class.getDeclaredField("pinAccepted");
        pinAccepted.setAccessible(true);
        assertTrue(pinAccepted.getBoolean(terminal));
        pinAccepted.setAccessible(false);

        Field remainingPinAttempts = TerminalImpl.class.getDeclaredField("remainingPinAttempts");
        remainingPinAttempts.setAccessible(true);
        assertEquals(3, remainingPinAttempts.getInt(terminal));
        remainingPinAttempts.setAccessible(false);
    }

    @Test
    public void pinNotAccepted() throws Exception {
        thrown.expect(InvalidPinException.class);
        thrown.expectMessage("You have 2 attempts");

        terminal.enterPin(0);
    }

    @Test
    public void accountIsLockedAfter3WrongPin() throws Exception {
        thrown.expect(AccountIsLockedException.class);

        try {
            terminal.enterPin(0);
        } catch (InvalidPinException ignoreFirstWrongPin) {
        }
        try {
            terminal.enterPin(0);
        } catch (InvalidPinException ignoreSecondWrongPin) {
        }
        terminal.enterPin(0);
        terminal.checkAccount();
    }

    @Test
    public void lockAccount() throws Exception {
        Method lockAccount = TerminalImpl.class.getDeclaredMethod("lockAccount");
        Method isAccountUnlocked = TerminalImpl.class.getDeclaredMethod("isAccountUnlocked");
        lockAccount.setAccessible(true);
        isAccountUnlocked.setAccessible(true);

        lockAccount.invoke(terminal);
        Thread.sleep(5000);
        assertTrue((boolean) isAccountUnlocked.invoke(terminal));

        thrown.expectCause(isA(AccountIsLockedException.class));
        lockAccount.invoke(terminal);
        Thread.sleep(4000);
        isAccountUnlocked.invoke(terminal);

        lockAccount.setAccessible(false);
        isAccountUnlocked.setAccessible(false);
    }

    @Test
    public void checkAccountWithoutAcceptedPin() throws Exception {
        thrown.expect(TerminalException.class);
        thrown.expectMessage("You must first enter the PIN");

        terminal.checkAccount();
    }

    @Test
    public void getMoneyWithoutAcceptedPin() throws Exception {
        thrown.expect(TerminalException.class);
        thrown.expectMessage("You must first enter the PIN");

        terminal.getMoney(0);
    }

    @Test
    public void putMoneyWithoutAcceptedPin() throws Exception {
        thrown.expect(TerminalException.class);
        thrown.expectMessage("You must first enter the PIN");

        terminal.putMoney(0);
    }

    @Test
    public void getMoneyNotMultipleOf100() throws Exception {
        thrown.expect(TerminalException.class);
        thrown.expectMessage("multiple of 100");

        terminal.enterPin(123456);
        terminal.getMoney(99);
    }

    @Test
    public void putMoneyNotMultipleOf100() throws Exception {
        thrown.expect(TerminalException.class);
        thrown.expectMessage("multiple of 100");

        terminal.enterPin(123456);
        terminal.putMoney(101);
    }

    @Test
    public void checkAccount() throws Exception {
        terminal.enterPin(123456);

        Field money = TerminalServer.class.getDeclaredField("money");
        money.setAccessible(true);
        money.setInt(server, 100500);
        money.setAccessible(false);

        assertEquals(100500, terminal.checkAccount());
    }

    @Test
    public void getMoreMoney() throws Exception {
        thrown.expect(NotEnoughMoneyException.class);

        terminal.enterPin(123456);
        terminal.putMoney(100);
        terminal.getMoney(200);
    }

    @Test
    public void getMoney() throws Exception {
        terminal.enterPin(123456);

        terminal.putMoney(1000);
        assertEquals(800, terminal.getMoney(200));
    }

    @Test
    public void putMoney() throws Exception {
        terminal.enterPin(123456);

        assertEquals(100500, terminal.putMoney(100500));
    }
}