package ru.job4j.problems;

/**
 * Проблемы с многопоточностью.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Problems {
    private int first;
    private int second;

    Problems(int first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Меняет местами значения полей
     */
    private void flip() {
        this.first = this.first + this.second;
        this.second = this.first - this.second;
        this.first = this.first - this.second;
    }

    /**
     * Иллюстрация "состояния гонки" (race condition). Метод запускает заданное количество
     * потоков, каждый из которых меняет местами поля {@code first} и {@code second}.
     * Для корректной работы метода (т.е. для достижения race condition) необходимо,
     * чтобы значения полей отличались.
     * @param numThreads количество потоков
     * @param cycles количество циклов перестановки для каждого потока
     * @return {@code true} - если возникло состояние гонки, {@code false} - в протвном случае.
     */
    public boolean illustrateRace(int numThreads, int cycles) {
        boolean detected = false;
        Thread[] threads = new Thread[numThreads];
        int sum = first + second;
        Runnable r = () -> {
                for (int i = 0; i < cycles; i++) {
                    this.flip();
                }
        };
        System.out.format("До перестановок: %d %d%n", this.first, this.second);
        for (int i = 0; i < numThreads; i++) {
            (threads[i] = new Thread(r)).start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.format("После перестановок: %d %d%n", this.first, this.second);
        if (this.first + this.second != sum) {
            detected = true;
            System.out.println("Race Condition detected!");
        }
        return detected;
    }

    /**
     * Иллюстрация взаимной блокировки
     * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/deadlock.html">
     *     The Java™ Tutorials</a>
     */
    public void illustrateDeadock() {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(() -> alphonse.bow(gaston)).start();
        new Thread(() -> gaston.bow(alphonse)).start();
    }

    static class Friend {
        private final String name;

        public Friend(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s"
                            + "  has bowed to me!%n",
                    this.name, bower.getName());
            bower.bowBack(this);
        }
        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s"
                            + " has bowed back to me!%n",
                    this.name, bower.getName());
        }
    }
}

