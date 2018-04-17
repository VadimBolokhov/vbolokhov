package ru.job4j.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Узел древовидной структуры.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <E> параметр типа элементов дерева
 */
public class Node<E extends Comparable<E>> {
    /** Список дочерних узлов */
    private final List<Node<E>> children = new ArrayList<>();
    /** Элемент коллекции */
    private final E value;

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param value элемент коллекции
     */
    public Node(E value) {
        this.value = value;
    }

    public E getValue() {
        return this.value;
    }

    /**
     * Добавляет дочерний узел
     * @param child добавляемый узел
     */
    public void add(Node<E> child) {
        this.children.add(child);
    }

    /**
     * Возвращает список дочерних узлов
     * @return список дочерних узлов
     */
    public List<Node<E>> leaves() {
        return this.children;
    }

    /**
     * Проверяет элементы на равенство
     * @param that элемент для сравнения
     * @return true - если элементы равны, false - в противном случае
     */
    public boolean eqValue(E that) {
        return this.value.compareTo(that) == 0;
    }
}
