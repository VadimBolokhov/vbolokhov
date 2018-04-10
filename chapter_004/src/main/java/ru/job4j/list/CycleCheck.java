package ru.job4j.list;

/**
 * Проверка цикличности связного списка.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class CycleCheck {
    /**
     * Проверяет цикличность связного списка
     * @param first первый узел связного списка
     * @param <T> параметр типа элементов связоного списка
     * @return true - если список содержит замыкание,
     * false - в противном случае
     */
    public <T> boolean hasCycle(Node first) {
        boolean hasCycle = false;
        Node<T> current = first;
        Node<T> target;
        while (current.next != null) {
            if (current.next == current) {
                hasCycle = true;
                break;
            }
            target = first;
            while (current != target) {
                if (current.next == target) {
                    hasCycle = true;
                    break;
                }
                target = target.next;
            }
            if (hasCycle) {
                break;
            }
            current = current.next;
        }
        return hasCycle;
    }
}

/**
 * Узел связного списка
 * @param <T> параметр типа элементов
 */
class Node<T> {
    T value;
    Node<T> next;

    public Node(T value) {
        this.value = value;
    }
}




