package ru.sbt.test.refactoring.command;

import ru.sbt.test.refactoring.common.Command;
import ru.sbt.test.refactoring.common.Unit;

import java.util.Deque;
import java.util.LinkedList;

public class CommandStack {

    private final Deque<Command> commandStack = new LinkedList<>();

    public void addCommand(Command command) {
        commandStack.add(command);
    }

    public void undoCommand(Unit unit) {
        if (!commandStack.isEmpty()) commandStack.pollLast().undo(unit);
    }
}
