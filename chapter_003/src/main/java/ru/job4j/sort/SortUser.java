package ru.job4j.sort;

import java.util.Comparator;
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

    /**
     * Сортирует пользователей по длине имени
     * @param users список пользователей
     * @return отсортированный список пользователей
     */
    public List<User> sortNameLength(List<User> users) {
                users.sort(new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return Integer.compare(
                                o1.getName().length(),
                                o2.getName().length()
                        );
                    }
                });
        return users;
    }

    /**
     * Сортирует список пользователей по алфавиту и возрасту
     * @param users список польхователей
     * @return
     */
    public List<User> sortByAllFields(List<User> users) {
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int result = o1.getName().compareTo(o2.getName());
                return result != 0 ? result : Integer.compare(o1.getAge(), o2.getAge());
            }
        });
        return users;
    }
}
