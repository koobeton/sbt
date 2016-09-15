package ru.sbt.test.refactoring;

public enum Orientation {

    NORTH {
        @Override
        public void moveForward(Position from) {
            from.moveNorth();
        }

        @Override
        public Orientation turnClockwise() {
            return EAST;
        }
    },
    WEST {
        @Override
        public void moveForward(Position from) {
            from.moveWest();
        }

        @Override
        public Orientation turnClockwise() {
            return NORTH;
        }
    },
    SOUTH {
        @Override
        public void moveForward(Position from) {
            from.moveSouth();
        }

        @Override
        public Orientation turnClockwise() {
            return WEST;
        }
    },
    EAST {
        @Override
        public void moveForward(Position from) {
            from.moveEast();
        }

        @Override
        public Orientation turnClockwise() {
            return SOUTH;
        }
    };

    public abstract void moveForward(Position from);

    public abstract Orientation turnClockwise();
}
