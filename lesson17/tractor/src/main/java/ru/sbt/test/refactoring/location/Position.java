package ru.sbt.test.refactoring.location;

public class Position {

    private static final int DEFAULT_X = 0;
    private static final int DEFAULT_Y = 0;

    private int x;
    private int y;

    public Position() {
        this(DEFAULT_X, DEFAULT_Y);
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveNorth() {
        y++;
    }

    public void moveEast() {
        x++;
    }

    public void moveSouth() {
        y--;
    }

    public void moveWest() {
        x--;
    }
}
