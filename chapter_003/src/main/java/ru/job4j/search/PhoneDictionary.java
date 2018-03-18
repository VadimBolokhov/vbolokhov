package ru.job4j.search;

import java.util.ArrayList;
import java.util.List;

/**
 * Телефонный справочник.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class PhoneDictionary {
    private List<Person> persons = new ArrayList<Person>();

    /**
     * Добавить запись
     * @param person запись
     */
    public void add(Person person) {
        this.persons.add(person);
    }

    /**
     * Вернуть список всех пользователей, которые содержат key в любых полях.
     * @param key ключ поиска
     * @return список подошедших пользователей
     */
    public List<Person> find(String key) {
        List<Person> result = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        fields.add(person -> { return person.getName(); });
        fields.add(person -> { return person.getSurname(); });
        fields.add(person -> { return person.getPhone(); });
        fields.add(person -> { return person.getAddress(); });
        for (Person person : this.persons) {
            for (Field field : fields) {
                if (field.getField(person).contains(key)) {
                    result.add(person);
                    break;
                }
            }
        }
        return result;
    }
}

interface Field {
    String getField(Person person);
}
