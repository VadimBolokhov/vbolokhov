package ru.job4j.professions;

/**
 * Доктор.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Doctor extends Profession {
    public Doctor(String name) {
        this.name = name;
        this.profession = "Доктор";
    }

    /**
     * Лечит больного
     * @param patient пациент
     * @return диагноз
     */
    public Diagnose heal(Patient patient) {
        return new Diagnose(this, patient);
    }
}
