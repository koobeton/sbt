package ru.sbt.test.refactoring;

public class Field {

    private final int width;
    private final int height;

    public Field(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean contains(Position position) {
        return width >= position.getX() && height >= position.getY();
    }
}
