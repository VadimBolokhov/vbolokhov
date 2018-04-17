package ru.job4j.tree;

import java.util.*;

/**
 * Древовидная структура.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <E> параметр типа элементов коллецкии
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    /** Корневой узел */
    private Node<E> root;
    /** Счётчик модификаций коллекции */
    private int modCount = 0;

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param root корневой узел
     */
    public Tree(E root) {
        this.root = new Node<>(root);
    }

    /**
     * Проверяет является ли дерево двоичным
     * @return true - если дерево двоичное, false - в противном случае
     */
    public boolean isBinary() {
        boolean result = true;
        Queue<Node<E>> data = new LinkedList<>();
        Node<E> current;
        data.offer(root);
        while (!data.isEmpty()) {
            current = data.poll();
            if (current.leaves().size() > 2) {
                result = false;
                break;
            }
            for (Node<E> child : current.leaves()) {
                data.offer(child);
            }
        }
        return result;
    }

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        if (!this.findBy(child).isPresent()) {
            Optional<Node<E>> target = this.findBy(parent);
            if (target.isPresent()) {
                target.get().add(new Node<>(child));
            }
            result = true;
        }
        return result;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                result = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int expectedModCount = modCount;
            Node<E> current;
            Queue<Node<E>> data = new LinkedList<>();

            {
                this.data.offer(root);
            }

            @Override
            public boolean hasNext() {
                return !this.data.isEmpty();
            }

            @Override
            public E next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                current = this.data.poll();
                E result = current.getValue();
                for (Node<E> child : current.leaves()) {
                    this.data.offer(child);
                }
                return result;
            }
        };
    }
}
