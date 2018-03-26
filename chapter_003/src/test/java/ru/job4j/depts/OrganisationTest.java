package ru.job4j.depts;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Organisation Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class OrganisationTest {
    /**
     * Тест метода addDepartment()
     */
    @Test
    public void whenAddDeptThenOrganisationHasSameDept() {
        Organisation org = new Organisation();
        String id = "K1\\SK1";
        org.addDepartment(id);
        String result = org.getDepts().get(0);
        assertThat(result, is("K1\\SK1"));
    }

    /**
     * Тест метода getDepts()
     */
    @Test
    public void whenGetDeptsThenReturnListOfDepts() {
        Organisation org = new Organisation();
        org.addDepartment("K1");
        org.addDepartment("K2");
        String[] result = org.getDepts().toArray(new String[0]);
        String[] expected = {"K1", "K2"};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода sort() с сортировкой по возрастанию,
     * первый элемент меньше второго
     */
    @Test
    public void whenSortAscendingFirstLessThanSecond() {
        Organisation org = new Organisation();
        org.addDepartment("K1");
        org.addDepartment("K2");
        org.sort(true);
        String[] result = org.getDepts().toArray(new String[0]);
        String[] expected = {"K1", "K2"};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода sort() с сортировкой по возрастанию,
     * первый элемент больше второго
     */
    @Test
    public void whenSortAscendingFirstGreaterThanSecond() {
        Organisation org = new Organisation();
        org.addDepartment("K2");
        org.addDepartment("K1\\SK1");
        org.sort(true);
        String[] result = org.getDepts().toArray(new String[0]);
        String[] expected = {"K1\\SK1", "K2"};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода sort() с сортировкой по убыванию,
     * первый элемент меньше второго
     */
    @Test
    public void whenSortDescendingFirstLessThanSecond() {
        Organisation org = new Organisation();
        org.addDepartment("K1\\SK1\\SSK1");
        org.addDepartment("K2\\SK1\\SSK1");
        org.sort(false);
        String[] result = org.getDepts().toArray(new String[0]);
        String[] expected = {"K2\\SK1\\SSK1", "K1\\SK1\\SSK1"};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода sort() с сортировкой по убыванию,
     * первый элемент больше второго
     */
    @Test
    public void whenSortDescendingFirstGreaterThanSecond() {
        Organisation org = new Organisation();
        org.addDepartment("K2");
        org.addDepartment("K1\\SK1");
        org.sort(false);
        String[] result = org.getDepts().toArray(new String[0]);
        String[] expected = {"K2", "K1\\SK1"};
        assertThat(result, is(expected));
    }
}