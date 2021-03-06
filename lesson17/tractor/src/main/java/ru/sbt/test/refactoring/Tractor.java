package ru.sbt.test.refactoring;

import ru.sbt.test.refactoring.command.Commands;
import ru.sbt.test.refactoring.command.CommandStack;
import ru.sbt.test.refactoring.common.Command;
import ru.sbt.test.refactoring.common.Unit;
import ru.sbt.test.refactoring.location.Orientation;
import ru.sbt.test.refactoring.location.Position;

public class Tractor implements Unit {

    private final Position position = new Position();
    private final Field field = new Field(5, 5);
    private Orientation orientation = Orientation.NORTH;
    private final CommandStack commandStack = new CommandStack();

    @Override
    public void move(String alias) {
        Command command = Commands.getCommand(alias);
        command.execute(this, field);
        commandStack.addCommand(command);
    }

    public void undo() {
        commandStack.undoCommand(this);
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
