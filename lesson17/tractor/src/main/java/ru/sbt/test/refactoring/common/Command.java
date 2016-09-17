package ru.sbt.test.refactoring.common;

import ru.sbt.test.refactoring.Field;

public interface Command {

    default void execute(Unit unit, Field field) {
    }
}
