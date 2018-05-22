package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Связный список.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <E> тип элемента коллекции
 */
@ThreadSafe
public class SimpleLinkedList<E> implements SimpleContainer<E> {
    /** Счётчик модификаций связного списка */
    @GuardedBy("this")
    private volatile int modCount = 0;
    /** Количество элементов в списке */
    @GuardedBy("this")
    private int size = 0;
    /** Первый элемент */
    @GuardedBy("this")
    private Node<E> first;
    /** Последний элемент */
    @GuardedBy("this")
    private Node<E> last;

    @SuppressWarnings("unchecked")
    @Override
    public synchronized void add(E e) {
        Node<E> link = new Node(e, last, null);
        if (last == null) {
            this.first = link;
        } else {
            this.last.next = link;
        }
        this.last = link;
        this.size++;
        this.modCount++;
    }

    @Override
    public synchronized E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> result = first;
        for (int i = 0; i < index; i++) {
            result = result.next;
        }
        return result.item;
    }

    /**
     * Возвращает последний элемент списка
     * @return последний элемент списка
     */
    public synchronized E getLast() {
        E result = null;
        if (this.last != null) {
            result = this.last.item;
        }
        return result;
    }

    /**
     * Удаляет последний элемент спсика
     */
    public synchronized void removeLast() {
        if (this.last.previous != null) {
            this.last.previous.next = null;
        }
        this.last = last.previous;
        this.size--;
        this.modCount++;
    }

    /**
     * Удаляет элемент по индексу
     * @param index номер элемента
     */
    public synchronized void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> target = first;
        for (int i = 0; i < index; i++) {
            target = target.next;
        }
        if (target.previous == null) {
            this.first = target.next;
        } else {
            target.previous.next = target.next;
        }
        if (target.next == null) {
            this.last = target.previous;
        } else {
            target.next.previous = target.previous;
        }
        this.modCount++;
        this.size--;
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> position = first;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                return position != null;
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (position == null) {
                    throw new NoSuchElementException();
                }
                E item = position.item;
                position = position.next;
                return item;
            }
        };
    }

    /**
     * Узел связного списка
     * @param <E> тип элемента коллекции
     */
    private static class Node<E> {
        E item;
        Node<E> previous;
        Node<E> next;

        Node(E item, Node<E> previous, Node<E> next) {
            this.item = item;
            this.previous = previous;
            this.next = next;
        }
    }
}
