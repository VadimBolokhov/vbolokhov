package ru.job4j.search;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * UserConvert Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class UserConvertTest {
    /**
     * Тест метода process()
     */
    @Test
    public void whenProcessUserList() {
        UserConvert convert = new UserConvert();
        List<User> list = new ArrayList<>();
        list.add(new User(123, "Vadim", "Saint-Petersburg"));
        String result = convert.process(list).get(123).getName();
        assertThat(result, is("Vadim"));
    }
}