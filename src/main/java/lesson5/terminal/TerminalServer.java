package lesson5.terminal;

import lesson5.terminal.exception.NotEnoughMoneyException;

class TerminalServer {

    private int money;

    int checkAccount() {
        return money;
    }

    int put(int amount) {
        return money += amount;
    }

    int get(int amount) {
        if (money < amount) throw new NotEnoughMoneyException();
        return money -= amount;
    }
}
