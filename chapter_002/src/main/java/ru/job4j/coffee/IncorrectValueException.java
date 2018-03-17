package ru.job4j.coffee;

/**
 * Исключение выбрасывается при неверно заданном достоинстве купюры
 * или при нехватке средств для покупки.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class IncorrectValueException extends Exception {
    IncorrectValueException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return this.getMessage();
    }
}
