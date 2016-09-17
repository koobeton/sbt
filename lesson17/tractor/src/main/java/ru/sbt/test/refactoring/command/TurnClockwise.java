package ru.sbt.test.refactoring.command;

import ru.sbt.test.refactoring.Field;
import ru.sbt.test.refactoring.common.Command;
import ru.sbt.test.refactoring.common.Unit;

public class TurnClockwise implements Command {

    @Override
    public void execute(Unit unit, Field field) {
        unit.setOrientation(unit.getOrientation().turnClockwise());
    }

    @Override
    public void undo(Unit unit) {
        unit.setOrientation(unit.getOrientation().turnClockwise());
        unit.setOrientation(unit.getOrientation().turnClockwise());
        unit.setOrientation(unit.getOrientation().turnClockwise());
    }
}
