package ru.job4j.vacancy;

import java.time.LocalDateTime;
import java.util.StringJoiner;

/**
 * Vacancy class.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Vacancy {
    /** Vacancy title */
    private String title;
    /** Description */
    private String description;
    /** Link to forum page */
    private String link;
    /** Vacancy post date */
    private LocalDateTime postDate;

    public Vacancy(String title, String description, String link, LocalDateTime posted) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.postDate = posted;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public LocalDateTime getPostDate() {
        return this.postDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(System.lineSeparator())
                .add(title)
                .add(description)
                .add(link)
                .add(postDate.toString())
                .toString();
    }
}
