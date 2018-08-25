package ru.job4j.vacancy;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Date parser.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class DateParser {
    private String dateString;

    /**
     * Parses date string from footer text to date and time
     * @param dateString the text to be parsed
     * @return {@code LocalDateTime} object
     * @throws ParseException if the text cannot be parsed to date
     */
    public LocalDateTime parseDateTime(String dateString) throws ParseException {
        this.dateString = dateString;
        return this.startsWithNumber()
                ? this.parseFormatted()
                : this.parseUnformatted();
    }

    private boolean startsWithNumber() {
        return Character.isDigit(this.dateString.charAt(0));
    }

    private LocalDateTime parseFormatted() {
        if (dateString.contains("май")) {
            dateString = dateString.replace("й", "я");
        }
        if (dateString.indexOf(" ") == 1) {
            dateString = 0 + dateString.trim();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yy, kk:mm");
        return LocalDateTime.parse(dateString, formatter);
    }

    private LocalDateTime parseUnformatted() throws ParseException {
        checkParsability();
        LocalDateTime dateTime;
        if (this.dateString.contains("сегодня")) {
            dateTime = this.getToday();
        } else {
            dateTime = this.getYesterday();
        }
        return dateTime;
    }

    private void checkParsability() throws ParseException {
        if (this.excludesTodayOrYesterday()) {
            throw new ParseException(
                    "Date string contains neither \"сегодня\" nor \"вчера\" substring", 0
            );
        }
    }

    private boolean excludesTodayOrYesterday() {
        return !this.dateString.contains("сегодня") && !this.dateString.contains("вчера");
    }

    private LocalDateTime getToday() {
        LocalDate today = LocalDate.now();
        return this.getLocalDateTime(today);
    }

    private LocalDateTime getLocalDateTime(LocalDate date) {
        LocalTime time = getLocalTime();
        return LocalDateTime.of(date, time);
    }

    private LocalTime getLocalTime() {
        int timeIndex = this.dateString.indexOf(':') - 2;
        String timeString = this.dateString.substring(timeIndex, timeIndex + 5);
        return LocalTime.parse(timeString);
    }

    private LocalDateTime getYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return this.getLocalDateTime(yesterday);
    }
}
