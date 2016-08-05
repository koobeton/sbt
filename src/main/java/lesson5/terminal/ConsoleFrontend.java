package lesson5.terminal;

import lesson5.terminal.exception.TerminalException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleFrontend {

    private static final int PIN = 123456;
    private final Terminal terminal;

    public ConsoleFrontend(Terminal terminal) {
        this.terminal = terminal;
    }

    public static void main(String... args) {

        ConsoleFrontend frontend = new ConsoleFrontend(new TerminalImpl(new TerminalServer(), new PinValidator(PIN)));

        System.out.println("\n*** Welcome to SBT Terminal! ***");

        mainWork:
        while (true) {
            showMenu();
            try {
                switch (readInt()) {
                    case 1:
                        System.out.println("Enter PIN:");
                        ((TerminalImpl) frontend.terminal).enterPin(readInt());
                        break;
                    case 2:
                        System.out.printf("You have: %d.%n", frontend.terminal.checkAccount());
                        break;
                    case 3:
                        System.out.println("Getting money.");
                        System.out.println("Enter amount:");
                        int amount = readInt();
                        System.out.printf("Operation has been successful, you have: %d.%n",
                                frontend.terminal.getMoney(amount));
                        break;
                    case 4:
                        System.out.println("Putting money.");
                        System.out.println("Enter amount:");
                        amount = readInt();
                        System.out.printf("Operation has been successful, you have: %d.%n",
                                frontend.terminal.putMoney(amount));
                        break;
                    case 0:
                        break mainWork;
                    default:
                        throw new TerminalException("You must enter the number between 0-4.");
                }
            } catch (TerminalException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("\n*** Goodbye! ***");
    }

    private static void showMenu() {
        System.out.println("\nAvailable options:");
        System.out.println("\t1 - Enter PIN");
        System.out.println("\t2 - Check account status");
        System.out.println("\t3 - Get money");
        System.out.println("\t4 - Put money");
        System.out.println("\t0 - Exit");
        System.out.println("Your choice:");
    }

    private static int readInt() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            return Integer.parseInt(in.readLine());
        } catch (IOException e) {
            throw new TerminalException("An I/O exception occurred: " + e.getMessage(), e);
        } catch (NumberFormatException e) {
            throw new TerminalException("You must enter the number: " + e.getMessage(), e);
        }
    }
}
