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
    private List<String> depts = new ArrayList<>();

    /**
     * Добавить код подразделения
     * @param id код подразделения
     */
    public void addDepartment(String id) {
        this.depts.add(id);
    }

    /**
     * Возвращает список подразделений организации
     * @return
     */
    public List<String> getDepts() {
        return this.depts;
    }

    /**
     * Сортирует список подразделений заданным образом
     * @param ascend true - сортировка по позрастанию,
     *               false - сортировка по убыванию
     */
    public void sort(boolean ascend) {
        int direction = ascend ? 1 : -1;
        this.depts.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = 0;
                String[] first = o1.split("\\\\");
                String[] second = o2.split("\\\\");
                int min = Math.min(first.length, second.length);
                for (int i = 0; i < min; i++) {
                    if (!first[i].equals(second[i])) {
                        result = direction * first[i].compareTo(second[i]);
                        break;
                    }
                }
                return result != 0 ? result : first.length - second.length;
            }
        });
    }
}
