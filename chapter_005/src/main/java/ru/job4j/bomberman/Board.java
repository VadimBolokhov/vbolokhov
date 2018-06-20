package ru.job4j.bomberman;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Игровое поле для игры "Бомбермэн".
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class Board {
    /** Игровое поле */
    @GuardedBy("this")
    private final ReentrantLock[][] board;
    /** Максимальное количество героев */
    private static final int MAX_PLAYERS = 2;
    /** Текущее количество игроков */
    @GuardedBy("this")
    private int players;
    /** Список игровых персоныжей */
    @GuardedBy("this")
    private List<GameCharacter> characters;
    /** {@code true} - когда игра запущена */
    @GuardedBy("this")
    private boolean running = false;

    Board(int xSize, int ySize) {
        this.board = new ReentrantLock[xSize][ySize];
        this.initBoard();
        this.characters = new ArrayList<>();
    }

    /**
     * Инициализация ячеек игрового поля
     */
    private synchronized void initBoard() {
        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board[x].length; y++) {
                this.board[x][y] = new ReentrantLock();
            }
        }
    }

    /**
     * Попытка перемещения персонажа на заданное поле
     * @param source текущая позиция
     * @param dest заданное поле
     * @return {@code true} - если попытка перемещения удалась, {@code false} - в противном случае
     */
    synchronized boolean move(Cell source, Cell dest) {
        boolean moved = false;
        if (this.isCorrectDestination(dest)
                && this.isClearDestination(dest)) {
            this.board[source.getX()][source.getY()].unlock();
            moved = true;
        }
        return moved;
    }

    private synchronized boolean isCorrectDestination(Cell target) {
        return 0 <= target.getX() && target.getX() < this.board.length
                && 0 <= target.getY() && target.getY() < this.board[0].length;
    }

    private synchronized boolean isClearDestination(Cell dest) {
        return this.board[dest.getX()][dest.getY()].tryLock();
    }

    /**
     * Добавляет героя на игровое поле и в список персонажей
     * @param hero добавляемый герой
     * @param position стартовая позиция героя
     * @param direction направление движения
     */
    public synchronized void addHero(Hero hero, Cell position, Facing direction) {
        if (this.players <= MAX_PLAYERS
                && this.isCorrectDestination(position)) {
            this.characters.add(hero);
            hero.setPosition(position);
            hero.setDirection(direction);
            players++;
        }
    }

    /**
     * Попытка поместить персонажа на стартовую позицию
     * @param position заданная позиция
     * @return {@code true} - если персонаж успешно размещен на заданной позиции,
     * {@code false} - в противном случае
     */
    synchronized boolean setStartingPositionSucceed(Cell position) {
        return this.board[position.getX()][position.getY()].tryLock();
    }

    /**
     * Запуск потоков игровых персонажей
     */
    public synchronized void startGame() {
        for (GameCharacter c : this.characters) {
            c.start();
        }
        try {
            while (this.running) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Прерывает все потоки персонажей
     */
    public synchronized void abortGame() {
        for (GameCharacter c : this.characters) {
            c.interrupt();
            this.running = false;
        }
    }

    public synchronized List<GameCharacter> getCharacters() {
        return this.characters;
    }
}
