package ru.job4j.index;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Префиксное дерево.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Trie {
    /** Корневой узел дерева */
    private Node root = new Node();

    /**
     * Добавляет слово в префиксное дерево
     * @param word добавляемое слово
     * @param index позиция слова в тексте
     */
    public void put(String word, Integer index) {
        Node current = this.root;
        for (char letter : word.toLowerCase().toCharArray()) {
            if (!current.children.containsKey(letter)) {
                current.children.put(letter, new Node());
            }
            current = current.children.get(letter);
        }
        current.entries.add(index);
    }

    /**
     * Возвращает список (множество) позиций в тексте для данного слова
     * @param word искомое слово
     * @return множество индексов, или {@code null} - если слово не найдено
     */
    public Set<Integer> getEntries(String word) {
        Set<Integer> result = null;
        boolean found = true;
        Node current = this.root;
        for (char letter : word.toLowerCase().toCharArray()) {
            if (!current.children.containsKey(letter)) {
                found = false;
                break;
            }
            current = current.children.get(letter);
        }
        if (found) {
            result = current.entries;
        }
        return result;
    }

    /**
     * Узел префиксного дерева.
     */
    class Node {
        Map<Character, Node> children = new TreeMap<>();
        Set<Integer> entries = new LinkedHashSet<>();
    }
}
