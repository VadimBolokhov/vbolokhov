package ru.job4j.sort;

/**
 * Класс пользователя.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class User implements Comparable<User> {
    /** Имя пользоваетля */
    private String name;
    /** Возраст пользователя */
    private int age;

    /**
     * Конструктор - создание объейта с заданными параметрами
     * @param name имя
     * @param age возраст
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public int compareTo(User user) {
        return Integer.compare(this.age, user.getAge());
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
