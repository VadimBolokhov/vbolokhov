package ru.job4j.professions;

import java.util.StringJoiner;

/**
 * Diagnose.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Diagnose {
    public Doctor doctor;
    public Patient patient;

    public Diagnose(Doctor doctor, Patient patient) {
        this.doctor = doctor;
        this.patient = patient;
    }

    public String toString() {
        StringJoiner diagnose = new StringJoiner(" ")
                .add("Доктор")
                .add(this.doctor.getName())
                .add("лечит пациента по имени")
                .add(this.patient.getName());
        return diagnose.toString();
    }
}
