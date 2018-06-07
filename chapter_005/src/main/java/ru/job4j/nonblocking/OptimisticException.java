package ru.job4j.nonblocking;

/**
 * Гененрируется при обновлении модели, когда модель изменена другим потоком.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class OptimisticException extends RuntimeException {
    /**
     * Конструктор - создание объекта
     */
    public OptimisticException(String msg) {
        super(msg);
    }
}
