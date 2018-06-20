package ru.job4j.bomberman;

/**
 * Направление движения игрового персонажа.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Facing {
    private Direction facing = Direction.EAST;

    Facing(Direction direction) {
        this.facing = direction;
    }

    /**
     * Ориентация персонажа на плоскости
     */
    enum Direction {
        EAST {
            public Direction left() {
                return NORTH;
            }

            public Direction right() {
                return SOUTH;
            }
        }, NORTH {
            public Direction left() {
                return WEST;
            }

            public Direction right() {
                return EAST;
            }
        }, WEST {
            public Direction left() {
                return SOUTH;
            }

            public Direction right() {
                return NORTH;
            }
        }, SOUTH {
            public Direction left() {
                return EAST;
            }

            public Direction right() {
                return WEST;
            }
        };

        public abstract Direction left();
        public abstract Direction right();
    }

    /**
     * Приращение координаты x при движении вперед
     * @return величина приращения
     */
    public int dx() {
        return (1 - this.facing.ordinal()) % 2;
    }

    /**
     * Приращение координаты y при движении вперед
     * @return величина приращения
     */
    public int dy() {
        return (this.facing.ordinal() - 2) % 2;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    public void turnLeft() {
        this.facing = this.facing.left();
    }

    public void turnRight() {
        this.facing = this.facing.right();
    }
}
