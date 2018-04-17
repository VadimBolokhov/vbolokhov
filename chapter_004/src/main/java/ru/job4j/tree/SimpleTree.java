package ru.job4j.tree;

import java.util.Optional;

/**
 * Интерфейс древовидной структуры.
 * @param <E> Параметр типа элементов коллекции
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавляет элемент в коллекцию
     * @param parent родительский элемент
     * @param child дочерний элемент
     * @return true - если элемент успешно добавлен,
     * false - если родительский элемент отсутствует или добавляемый элемент уже есть в коллекции
     */
    boolean add(E parent, E child);

    /**
     * Осуществляет поиск заданного элемента в коллекции
     * @param value заданный элемент
     * @return Optional, содержащий найденный элемент (если есть)
     */
    Optional<Node<E>> findBy(E value);
}
