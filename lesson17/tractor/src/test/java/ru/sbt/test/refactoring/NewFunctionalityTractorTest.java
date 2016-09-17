package ru.sbt.test.refactoring;

import org.junit.Before;
import org.junit.Test;
import ru.sbt.test.refactoring.location.Orientation;

import static org.junit.Assert.*;

public class NewFunctionalityTractorTest {

    private Tractor tractor;

    @Before
    public void setUp() throws Exception {
        tractor = new Tractor();
    }

    @Test
    public void dontMoveOnUnexpectedCommands() throws Exception {
        tractor.move("Unexpected command");

        assertEquals(0, tractor.getPositionX());
        assertEquals(0, tractor.getPositionY());
        assertEquals(Orientation.NORTH, tractor.getOrientation());
    }

    @Test
    public void moveBackward() throws Exception {
        tractor.move("F");
        tractor.move("F");
        tractor.move("T");
        tractor.move("F");
        tractor.move("F");
        tractor.move("F");
        tractor.move("F");
        tractor.move("F");

        assertEquals(5, tractor.getPositionX());
        assertEquals(2, tractor.getPositionY());
        assertEquals(Orientation.EAST, tractor.getOrientation());

        tractor.move("B");
        tractor.move("B");
        tractor.move("B");
        tractor.move("B");

        assertEquals(1, tractor.getPositionX());
        assertEquals(2, tractor.getPositionY());
        assertEquals(Orientation.EAST, tractor.getOrientation());
    }

    @Test
    public void turnCounterClockwise() throws Exception {
        tractor.move("TC");

        assertEquals(Orientation.WEST, tractor.getOrientation());
    }
}