package ru.job4j.tracker;

/**
 * Проверка корректности ввода пользователя.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ValidateInput extends ConsoleInput {
    @Override
    public int ask(String question, int[] range) {
        boolean valid = false;
        int key = -1;
        while (!valid) {
            try {
                key = super.ask(question, range);
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
