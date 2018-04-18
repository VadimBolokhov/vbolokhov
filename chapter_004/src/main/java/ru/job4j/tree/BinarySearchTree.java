package ru.job4j.tree;

import java.util.*;

/**
 * Двоичное дерево.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <E> параметр типа элементов коллецкии
 */
public class BinarySearchTree<E extends Comparable<E>> implements Iterable<E> {
    /** Корневой узел дерева */
    private BinaryNode<E> root;
    /** Счётчик модификаций коллекции */
    private int modCount;

    /**
     * Добавляет элемент в коллекцию
     * @param value добавляемый элемент
     * @return true - если элемент успешно добавлен в дерево,
     * false - если элемент уже существует
     */
    public boolean add(E value) {
        boolean result = true;
        if (this.root == null) {
            this.root = new BinaryNode<>(value);
        } else {
            result = this.insert(value, root);
        }
        if (result) {
            modCount++;
        }
        return result;
    }

    /**
     * Используется для рекурсивного добавления элементов в коллекцию
     * @param value добавляемый элемент
     * @param current текущий узел дерева
     * @return true - если элемент успешно добавлен в дерево,
     * false - если элемент уже существует
     */
    private boolean insert(E value, BinaryNode<E> current) {
        boolean result = true;
        if (value.compareTo(current.value) < 0) {
            if (current.left == null) {
                current.left = new BinaryNode<>(value);
            } else {
                result = this.insert(value, current.left);
            }
        } else if (value.compareTo(current.value) > 0) {
            if (current.right == null) {
                current.right = new BinaryNode<>(value);
            } else {
                result = this.insert(value, current.right);
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Поиск элемента в коллекции
     * @param key искомый элемент
     * @return {@code Optional}, содержащий найденный элемент, если есть
     */
    public Optional<BinaryNode<E>> find(E key) {
        Optional<BinaryNode<E>> result = Optional.empty();
        BinaryNode<E> current = root;
        while (current != null) {
            if (current.value.equals(key)) {
                result = Optional.of(current);
                break;
            }
            if (key.compareTo(current.value) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int expectedModCount = modCount;
            BinaryNode<E> current = root;
            Deque<BinaryNode<E>> nodeStack = new LinkedList<>();

            {
                this.nodeStack.push(root);
            }

            @Override
            public boolean hasNext() {
                return root != null && !this.nodeStack.isEmpty();
            }

            @Override
            public E next() {
                if (this.expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                if (this.current.right != null) {
                    this.nodeStack.push(this.current.right);
                }
                if (this.current.left != null) {
                    this.current = this.current.left;
                } else {
                    this.current = this.nodeStack.pop();
                }
                return this.current.value;
            }
        };
    }
}

/**
 * Узел двоичного дерева.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <E> параметр типа элементов коллецкии
 */
class BinaryNode<E> {
    E value;
    BinaryNode<E> left;
    BinaryNode<E> right;

    BinaryNode(E value) {
        this.value = value;
    }
}
