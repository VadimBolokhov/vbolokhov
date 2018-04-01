package ru.job4j.iterator;

import java.util.Iterator;

/**
 * Преобразователь итераторов.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Converter {
    /**
     * Преобразует 'итератор итераторов' в один большой итератор
     * @param it заданный составной итератор
     * @return результат преобразования
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {

        return new Iterator<Integer>() {
            private Iterator<Integer> position = it.next();

            @Override
            public boolean hasNext() {
                boolean result = false;
                if (this.position.hasNext()) {
                    result = true;
                } else {
                    while (it.hasNext()) {
                        this.position = it.next();
                        if (this.position.hasNext()) {
                            result = true;
                            break;
                        }
                    }
                }
                return result;
            }

            @Override
            public Integer next() {
                if (!this.position.hasNext()) {
                    this.hasNext();
                }
                return this.position.next();
            }
        };
    }
}
