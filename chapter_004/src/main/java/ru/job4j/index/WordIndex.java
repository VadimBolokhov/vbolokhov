package ru.job4j.index;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

/**
 * Индексация текста.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class WordIndex {
    /** Хранилище слов */
    Trie words = new Trie();

    /**
     * Считывает содержимое текстового файла, помещает все слова в префиксное дерево
     * @param filename имя файла
     * @throws IOException если файл открыть не удалось
     */
    public void loadFile(String filename) throws IOException {
        Scanner scan = new Scanner(Paths.get(filename)).useDelimiter("[\\W]+");
        int counter = 0;
        while (scan.hasNext()) {
            String word = scan.next();
            this.words.put(word, counter);
            counter++;
        }
    }

    /**
     * Возвращает список (множество) позиций в тексте для данного слова
     * @param searchWord искомое слово
     * @return множество индексов, или {@code null} - если слово не найдено
     */
    public Set<Integer> getIndexes4Word(String searchWord) {
        return words.getEntries(searchWord);
    }
}
