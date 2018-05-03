package ru.job4j.pingpong;

import javafx.scene.shape.Rectangle;

/**
 * Движение четырёхугольника в окне.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class RectangleMove implements Runnable {
    /** Четырёхугольная фигура */
    private final Rectangle rect;
    /** Скорость движения по горизонтали */
    private double velocityX = 1.0;
    /** Скорость движения по вертикали */
    private double velocityY = -0.8d;
    /** Флаг остановки */
    private boolean stop = false;

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param rect четырёхугольник
     */
    public RectangleMove(final Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Устанавливает {@code true} для флага остановки
     */
    public void setStop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            this.rect.setX(this.rect.getX() + this.velocityX);
            this.rect.setY(this.rect.getY() + this.velocityY);
            if (this.rect.getX() > 300 || this.rect.getX() < 0) {
                this.velocityX = -this.velocityX;
            }
            if (this.rect.getY() > 300 || this.rect.getY() < 0) {
                this.velocityY = -this.velocityY;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
