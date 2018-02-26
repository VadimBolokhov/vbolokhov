package ru.job4j.professions;

/**
 * Учитель.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Teacher extends Profession {
    public Teacher(String name) {
        this.name = name;
        this.profession = "Учитель";
    }

    /**
     * Учит студента
     * @param student заданный студент
     */
    public void teach(Student student) {

    }
}
