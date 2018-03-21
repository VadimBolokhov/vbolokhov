package ru.job4j.tracker;

import java.util.List;
import java.util.Scanner;

/**
 * ConsoleInput.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ConsoleInput implements Input {
    /** Считывание консольного ввода пользователя */
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public int ask(String question, List<Integer> range) {
        System.out.println(question);
        int key = Integer.valueOf(scanner.nextLine());
        boolean exist = false;
        for (int i : range) {
            if (key == i) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Пункта меню не существует");
        }
        return key;
    }
}
