package ru.sbt.test.refactoring.command;

import ru.sbt.test.refactoring.Field;
import ru.sbt.test.refactoring.TractorInDitchException;
import ru.sbt.test.refactoring.common.Command;
import ru.sbt.test.refactoring.common.Unit;

public class MoveForward implements Command {

    @Override
    public void execute(Unit unit, Field field) {
        unit.getOrientation().moveForward(unit.getPosition());
        if (!field.contains(unit.getPosition())) throw new TractorInDitchException();
    }
}
