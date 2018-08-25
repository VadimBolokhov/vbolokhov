package ru.job4j.vacancy;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.text.ParseException;
import java.time.LocalDateTime;

import java.time.Year;
import java.util.LinkedList;
import java.util.List;

/**
 * Vacancy parser.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class VacancyParser {
    private static Logger logger = LogManager.getLogger("VacancyParser");
    /** Date of the last update */
    private LocalDateTime lastUpdate;
    /** Parsing condition.
     * Gets 'false' when there are no more vacancies posted since last update */
    private boolean afterLastUpdate = true;
    /** Date parser */
    private DateParser dateParser = new DateParser();

    VacancyParser() {
        this(LocalDateTime.of(Year.now().getValue(), 1, 1, 0, 0));
    }

    VacancyParser(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Vacancy> getVacancies() {
        return this.parseForum();
    }

    private List<Vacancy> parseForum() {
        List<Vacancy> result = new LinkedList<>();
        for (int i = 1; this.afterLastUpdate; i++) {
            result.addAll(this.parsePage(i));
        }
        return result;
    }

    private List<Vacancy> parsePage(int number) {
        List<Vacancy> result = new LinkedList<>();
        try {
            Elements rows = this.getRowsFromForumTable(number);
            result = this.getVacanciesFromRows(rows);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    private Elements getRowsFromForumTable(int number) throws IOException {
        String searchResult =
                "http://www.sql.ru/forum/actualsearch.aspx"
                        + "?search=java+%7escript&sin=1&bid=66&a=&ma=0&dt=356&s=4&so=1&pg=";
        Document doc = Jsoup.connect(searchResult + number).get();
        Element element = doc.getElementsByClass("forumTable").first();
        return element.select("tr");
    }

    private List<Vacancy> getVacanciesFromRows(Elements rows) throws IOException {
        List<Vacancy> result = new LinkedList<>();
        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            try {
                Vacancy current = this.parseRow(row);
                if (current.getPostDate().isBefore(this.lastUpdate)) {
                    this.afterLastUpdate = false;
                    break;
                }
                result.add(current);
            } catch (ParseException e) {
                logger.error("Cannot parse string to date", e);
            }
        }
        return result;
    }

    private Vacancy parseRow(Element element) throws IOException, ParseException {
        Element link = element.select("a").first();
        String url = link.attr("href");
        return this.getVacancy(url);
    }

    public Vacancy getVacancy(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).get();
        String title = this.getTitle(doc);
        String desc = this.getDescription(doc);
        LocalDateTime postDate = getPostDate(doc);
        return new Vacancy(title, desc, url, postDate);
    }

    private String getTitle(Document doc) {
        Element header = doc.getElementsByClass("messageHeader").first();
        header.select("span").remove();
        return header.text();
    }

    private String getDescription(Document doc) {
        Element message = doc.getElementsByClass("msgTable").first();
        return message.getElementsByClass("msgBody").next().text();
    }

    private LocalDateTime getPostDate(Document doc) throws ParseException {
        Element footer = doc.getElementsByClass("msgFooter").first();
        String dateString = this.getDateString(footer);
        return this.dateParser.parseDateTime(dateString);
    }

    private String getDateString(Element footer) {
        footer.select("a").remove();
        String footerText = footer.text();
        int dateEnd = footerText.indexOf(':') + 3;
        return footerText.substring(0, dateEnd);
    }
}
