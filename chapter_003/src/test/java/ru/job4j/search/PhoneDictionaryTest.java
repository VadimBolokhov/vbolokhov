package ru.job4j.search;

import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * PhoneDictionary Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class PhoneDictionaryTest {
    /** Телефонный справочник */
    private PhoneDictionary phones;

    /**
     * Инициалищация полей
     */
    @Before
    public void init() {
        this.phones = new PhoneDictionary();
    }

    /**
     * Тест метода find() с поиском по имени;
     */
    @Test
    public void whenFindByName() {
        PhoneDictionary phones = new PhoneDictionary();
        phones.add(
                new Person("Vadim", "Bolokhov", "534872", "Saint-Petersburg")
        );
        List<Person> persons = phones.find("Vadim");
        assertThat(persons.iterator().next().getSurname(), is("Bolokhov"));
    }

    /**
     * Тест метода find() с поиском по фамилии;
     */
    @Test
    public void whenFindBySurname() {
        PhoneDictionary phones = new PhoneDictionary();
        phones.add(
                new Person("Vadim", "Bolokhov", "534872", "Saint-Petersburg")
        );
        List<Person> persons = phones.find("Bolokhov");
        assertThat(persons.iterator().next().getName(), is("Vadim"));
    }

    /**
     * Тест метода find() с поиском по номеру телефона;
     */
    @Test
    public void whenFindByPhone() {
        PhoneDictionary phones = new PhoneDictionary();
        phones.add(
                new Person("Vadim", "Bolokhov", "534872", "Saint-Petersburg")
        );
        List<Person> persons = phones.find("534872");
        assertThat(persons.iterator().next().getName(), is("Vadim"));
    }

    /**
     * Тест метода find() с поиском по адресу;
     */
    @Test
    public void whenFindByAddress() {
        PhoneDictionary phones = new PhoneDictionary();
        phones.add(
                new Person("Vadim", "Bolokhov", "534872", "Saint-Petersburg")
        );
        List<Person> persons = phones.find("Saint-Petersburg");
        assertThat(persons.iterator().next().getName(), is("Vadim"));
    }
}