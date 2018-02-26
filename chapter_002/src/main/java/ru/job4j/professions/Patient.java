package ru.job4j.professions;

/**
 * Пациент.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Patient {
    public String name;

    public Patient(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
