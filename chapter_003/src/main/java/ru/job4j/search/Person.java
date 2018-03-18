package ru.job4j.search;

/**
 * Запись телефонного справочника.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Person {
    private String name;
    private String surname;
    private String phone;
    private String address;

    /**
     * Конструктор - создание объекта с заданными параметрами
     * @param name имя
     * @param surname фамилия
     * @param phone номер телефона
     * @param address адрес
     */
    public Person(String name, String surname, String phone, String address) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getAddress() {
        return this.address;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", this.name, this.surname,
                this.phone, this.address
        );
    }
}
