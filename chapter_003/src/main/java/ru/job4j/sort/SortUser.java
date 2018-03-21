package ru.job4j.sort;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Сортировка пользователей.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SortUser {
    /**
     * Сортирует пользователей по возрасту
     * @param list список пользователей
     * @return отсортированное множество пользователей
     */
    public Set<User> sort(List<User> list) {
        return new TreeSet<>(list);
    }
}
