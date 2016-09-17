package ru.sbt.test.refactoring;

import ru.sbt.test.refactoring.command.Commands;
import ru.sbt.test.refactoring.common.Unit;
import ru.sbt.test.refactoring.location.Orientation;
import ru.sbt.test.refactoring.location.Position;

public class Tractor implements Unit {

    private final Position position = new Position();
    private final Field field = new Field(5, 5);
    private Orientation orientation = Orientation.NORTH;

    public void move(String command) {
        Commands.getCommand(command).execute(this, field);
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
