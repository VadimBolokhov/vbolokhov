package ru.job4j.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

/**
 * ConvertList Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SortUserTest {
    /**
     * Тест меторда sort()
     */
    @Test
    public void whenSortUserListByAge() {
        SortUser su = new SortUser();
        List<User> list = new ArrayList<>();
        list.add(new User("John", 50));
        list.add(new User("Alex", 40));
        list.add(new User("Mike", 55));
        TreeSet<User> result = (TreeSet<User>)su.sort(list);
        assertThat(result.first().getName(), is("Alex"));
        assertThat(result.last().getName(), is("Mike"));
    }
}