package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Динамический массив.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class SimpleList<E> implements SimpleContainer<E> {
    /** Массив для хранения элементов */
    @GuardedBy("this")
    private Object[] container;
    /** Номер ячейки массива, куда будет добавлен слудующий элемент */
    private int size = 0;
    /** Размер массива по умолчанию */
    private static final int DEFAULT_LENGTH = 10;
    /** Счётчик модификаций массива */
    private int modCount = 0;


    /**
     * Конструктор - создание нового объекта
     */
    public SimpleList() {
        this.container = new Object[DEFAULT_LENGTH];
    }

    @Override
    public synchronized void add(E e) {
        if (this.size == this.container.length - 1) {
            this.container = Arrays.copyOf(
                    this.container,
                    2 * this.container.length
            );
        }
        this.container[size++] = e;
        this.modCount++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized E get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }

        return  (E) this.container[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            int expectedModCount = modCount;
            int position = 0;
            private final Object[] snapshot = container;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (position >= size) {
                    throw new NoSuchElementException();
                }
                return (E) snapshot[position++];
            }
        };
    }
}
