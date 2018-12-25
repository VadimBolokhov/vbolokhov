package ru.job4j.cinema.service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Converts JSON request to String.
 * @author Vadim Bolokhov
 */
public class JsonRequestConverter {

    public String convertRequestToString(HttpServletRequest req) throws IOException {
        StringBuilder json = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line = reader.readLine();
            while (line != null) {
                json.append(line);
                line = reader.readLine();
            }
        }
        return json.toString();
    }
}
