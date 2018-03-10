package ru.job4j.tracker;

/**
 * Проверка корректности ввода пользователя.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ValidateInput implements Input {
    private final Input input;

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param input интерфейс ввода
     */
    public ValidateInput(final Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, int[] range) {
        boolean valid = false;
        int key = -1;
        while (!valid) {
            try {
                key = this.input.ask(question, range);
                valid = true;
            } catch (MenuOutException moe) {
                System.out.println(moe.getMessage());
            } catch (NumberFormatException nfe) {
                System.out.println("Введите корректное значение");
            }
        }
        return key;
    }
}
