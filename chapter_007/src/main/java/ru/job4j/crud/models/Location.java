package ru.job4j.crud.models;

import java.util.*;

/**
 * Map of countries and cities.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public enum Location {
    /** Singleton instance */
    INSTANCE;
    /** Map of countries and cities */
    private TreeMap<String, TreeSet<String>> cities = new TreeMap<>();

    Location() {
        this.cities.put("Russia", new TreeSet<>(Arrays.asList("Moscow", "Saint-Petersburg")));
        this.cities.put("Germany", new TreeSet<>(Arrays.asList("Berlin", "Dresden")));
        this.cities.put("Italy", new TreeSet<>(Arrays.asList("Rome", "Milan")));
    }

    public List<String> getCities(String country) {
        return new LinkedList<>(this.cities.get(country));
    }

    public List<String> getCountries() {
        return new LinkedList<>(this.cities.keySet());
    }
}
