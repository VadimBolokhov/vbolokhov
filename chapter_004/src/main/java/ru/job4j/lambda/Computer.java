package ru.job4j.lambda;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Computer class.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Computer {

    /**
     * Computes function values in a given range
     * @param start range start
     * @param end range end
     * @param func function
     * @return list of computed values
     */
    public List<Double> diapason(int start, int end, Function<Double, Double> func) {
        List<Double> result = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            result.add(func.apply((double) i));
        }
        return result;
    }
}
