package ru.job4j.search;

import java.util.HashMap;
import java.util.List;

/**
 * Конвертор списка пользователей.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserConvert {
    /**
     * Конвертирует список пользователей в хэш-таблицу
     * @param list заданный список пользователей
     * @return результат конвертации
     */
    public HashMap<Integer, User> process(List<User> list) {
        HashMap<Integer, User> result = new HashMap<>();
        for (User user : list) {
            result.put(user.getId(), user);
        }
        return result;
    }
}
