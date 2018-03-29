package ru.job4j.depts;

import org.junit.Before;
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
    private Organisation org;

    @Before
    public void init() {
        org = new Organisation();
    }

    /**
     * Тест метода addDepartment()
     */
    @Test
    public void whenAddDeptThenOrganisationHasSameDept() {
        String id = "K1\\SK1";
        this.org.addDepartment(id);
        String result = org.getDepts().get(0);
        assertThat(result, is("K1\\SK1"));
    }

    /**
     * Тест метода getDepts()
     */
    @Test
    public void whenGetDeptsThenReturnListOfDepts() {
        this.org.addDepartment("K1");
        this.org.addDepartment("K2");
        String[] result = org.getDepts().toArray(new String[0]);
        String[] expected = {"K1", "K2"};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода sortAscending(),
     * первый элемент меньше второго
     */
    @Test
    public void whenSortAscendingFirstLessThanSecond() {
        this.org.addDepartment("K1");
        this.org.addDepartment("K1\\SK1");
        this.org.sortAscending();
        String[] result = this.org.getDepts().toArray(new String[0]);
        String[] expected = {"K1", "K1\\SK1"};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода sortAscending(),
     * первый элемент больше второго
     */
    @Test
    public void whenSortAscendingFirstGreaterThanSecond() {
        this.org.addDepartment("K2");
        this.org.addDepartment("K1\\SK1");
        this.org.sortAscending();
        String[] result = this.org.getDepts().toArray(new String[0]);
        String[] expected = {"K1\\SK1", "K2"};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода sortDescending(),
     * первый элемент меньше второго
     */
    @Test
    public void whenSortDescendingFirstLessThanSecond() {
        this.org.addDepartment("K1");
        this.org.addDepartment("K1\\SK1");
        this.org.sortDescending();
        String[] result = this.org.getDepts().toArray(new String[0]);
        String[] expected = {"K1", "K1\\SK1"};
        assertThat(result, is(expected));
    }

    /**
     * Тест метода sortDescending() с сортировкой по убыванию,
     * первый элемент больше второго
     */
    @Test
    public void whenSortDescendingFirstGreaterThanSecond() {
        this.org.addDepartment("K2");
        this.org.addDepartment("K1\\SK1");
        this.org.sortDescending();
        String[] result = this.org.getDepts().toArray(new String[0]);
        String[] expected = {"K2", "K1\\SK1"};
        assertThat(result, is(expected));
    }
}