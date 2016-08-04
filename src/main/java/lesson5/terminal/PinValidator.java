package lesson5.terminal;

class PinValidator {

    private final int pin;

    PinValidator(int pin) {
        this.pin = pin;
    }

    boolean validate(int pin) {
        return this.pin == pin;
    }
}
