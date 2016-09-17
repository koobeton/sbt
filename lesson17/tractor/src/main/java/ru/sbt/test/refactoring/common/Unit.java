package ru.sbt.test.refactoring.common;

import ru.sbt.test.refactoring.location.Orientation;
import ru.sbt.test.refactoring.location.Position;

public interface Unit {

    Position getPosition();

    Orientation getOrientation();

    void setOrientation(Orientation orientation);
}
