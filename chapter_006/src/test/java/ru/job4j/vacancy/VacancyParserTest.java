package ru.job4j.vacancy;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * MagnetApp Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@Ignore
public class VacancyParserTest {
    @Test
    public void whenParseForumPageThenReturnsVacancy() throws ParseException, IOException {
        String url = "http://www.sql.ru/forum/1298908/lead-java-developer-sap-hybris-150-230k";
        LocalDateTime postDate = LocalDateTime.of(2018, 7, 18, 1, 37);
        String title = "Lead Java Developer SAP Hybris >150-230k";
        Vacancy result = new VacancyParser().getVacancy(url);
        assertThat(result.getPostDate(), is(postDate));
        assertThat(result.getTitle(), is(title));
    }
}