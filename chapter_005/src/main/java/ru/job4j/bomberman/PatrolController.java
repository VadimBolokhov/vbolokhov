package ru.job4j.bomberman;

/**
 * Перемещение персонажа в автоматическом режиме.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class PatrolController extends Controller {
    PatrolController(GameCharacter character) {
        super(character);
    }

    @Override
    public void walk() {
        try {
            while (true) {
                Thread.sleep(1000);
                this.tryMove();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tryMove() throws InterruptedException {
        while (!this.moveForward()) {
            Thread.sleep(500);
            if (!this.moveForward()) {
                this.turnLeft();
            }
        }
    }
}
