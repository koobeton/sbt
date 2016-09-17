package ru.sbt.test.refactoring.command;

import ru.sbt.test.refactoring.common.Command;

import java.util.HashMap;
import java.util.Map;

public class Commands {

    private final static Map<String, Command> COMMANDS = new HashMap<>();
    private final static Command STAND = new Command() {
    };

    static {
        setCommand("F", new MoveForward());
        setCommand("T", new TurnClockwise());
    }

    public static void setCommand(String alias, Command command) {
        COMMANDS.put(alias, command);
    }

    public static Command getCommand(String alias) {
        return COMMANDS.getOrDefault(alias, STAND);
    }
}
