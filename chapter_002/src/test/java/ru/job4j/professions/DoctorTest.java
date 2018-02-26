package ru.job4j.professions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Doctor Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class DoctorTest {

    /**
     * Тест метода heal()
     */
    @Test
    public void whenHealPatientThenReturnDiagnoseString() {
        Doctor doctor = new Doctor("Иван");
        Patient patient = new Patient("Сергей");
        String result = doctor.heal(patient).toString();
        String expected = "Доктор Иван лечит пациента по имени Сергей";
        assertThat(result, is(expected));
    }
}