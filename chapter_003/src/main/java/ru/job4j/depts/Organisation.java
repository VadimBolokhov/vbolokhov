package ru.job4j.depts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Класс организации - предназначен для хранения списка подразделений.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Organisation {
    /** Список кодов подразделений организации */
    private List<String[]> depts = new ArrayList<>();

    /**
     * Добавить код подразделения
     * @param id код подразделения
     */
    public void addDepartment(String id) {
        this.depts.add(id.split("\\\\"));
    }

    /**
     * Возвращает список подразделений организации
     * @return
     */
    public List<String> getDepts() {
        List<String> result = new ArrayList<>();
        for (String[] dept : this.depts) {
            result.add(String.join("\\", dept));
        }
        return result;
    }

    /**
     * Сортирует список подразделений по возрастанию
     */
    public void sortAscending() {
        this.depts.sort(new Comparator<String[]>() {
            @Override
            public int compare(String[] first, String[] second) {
                int result = 0;
                int steps = Math.min(first.length, second.length);
                for (int i = 0; i < steps; i++) {
                    if (!first[i].equals(second[i])) {
                        result = first[i].compareTo(second[i]);
                        break;
                    }
                }
                return result != 0 ? result : first.length - second.length;
            }
        });
    }

    /**
     * Сортирует список подразделений по убыванию
     */
    public void sortDescending() {
        this.depts.sort(new Comparator<String[]>() {
            @Override
            public int compare(String[] first, String[] second) {
                int result = 0;
                int steps = Math.min(first.length, second.length);
                for (int i = 0; i < steps; i++) {
                    if (!first[i].equals(second[i])) {
                        result = -first[i].compareTo(second[i]);
                        break;
                    }
                }
                return result != 0 ? result : first.length - second.length;
            }
        });
    }
}
