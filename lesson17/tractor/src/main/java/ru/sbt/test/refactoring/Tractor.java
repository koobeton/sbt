package ru.sbt.test.refactoring;

public class Tractor {

    private static final String COMMAND_FORWARD = "F";
    private static final String COMMAND_TURN_CLOCKWISE = "T";

    private final Position position = new Position();
    private final Field field = new Field(5, 5);
    private Orientation orientation = Orientation.NORTH;

    public void move(String command) {
        if (COMMAND_FORWARD.equals(command)) moveForwards();
        else if (COMMAND_TURN_CLOCKWISE.equals(command)) turnClockwise();
    }

    public void moveForwards() {
        orientation.moveForward(position);
        if (!field.contains(position)) throw new TractorInDitchException();
    }

    public void turnClockwise() {
        orientation = orientation.turnClockwise();
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
