package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Хэш-отображение
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <K> параметр типа ключа
 * @param <V> параметр типа значения
 */
public class SimpleMap<K, V> implements Iterable<K> {
    /** Хранилище элементов */
    private Entry<K, V>[] container;
    /** Размер хранилища по умолчанию */
    private static final int DEFAULT_LENGTH = 10;
    /** Счётчик модификаций коллекции */
    private int modCount = 0;
    /** Количество заполненных ячеек массива */
    private int size = 0;

    /**
     * Конструктор - создание отображения
     */
    public SimpleMap() {
        this(DEFAULT_LENGTH);
    }

    /**
     * Конструктор - создание отображения с заданной длиной массива
     * @param length длина массива
     */
    public SimpleMap(int length) {
        this.container = new Entry[length];
    }

    /**
     * Добавляет запись в отображение
     * @param key ключ
     * @param value значение
     * @return true - если ключа в коллекции нет, false - если ключ есть
     */
    public boolean insert(K key, V value) {
        if (this.container.length == this.size) {
            this.resize();
        }
        boolean result = true;
        int hash = key.hashCode();
        int index = hash % this.container.length;
        Entry<K, V> target = this.container[index];
        if (target != null && target.hash == hash && target.key.equals(key)) {
            this.container[index].value = value;
            result = false;
        } else {
            this.container[index] = new Entry<>(hash, key, value);
        }
        this.size++;
        this.modCount++;
        return result;
    }

    /**
     * Увеличивает размер массива при заполнении
     */
    private void resize() {
        int newLength = this.container.length == 0 ? DEFAULT_LENGTH
                : 2 * this.container.length;
        Entry<K, V>[] newArray = new Entry[newLength];
        for (int i = 0; i < this.container.length; i++) {
            if (this.container[i] != null) {
                int index = this.container[i].hash % newArray.length;
                newArray[index] = this.container[i];
            }
        }
        this.container = newArray;
    }

    /**
     * Извлекает значение соответствующее заданному ключу
     * @param key ключ
     * @return значение
     */
    public V get(K key) {
        int index = key.hashCode() % this.container.length;
        Entry<K, V> target = this.container[index];
        return target == null ? null : target.value;
    }

    /**
     * Удаляет запись из коллекции с заданным ключом
     * @param key ключ
     * @return true - если ключ найден и запись удалена, false - в противном случае
     */
    public boolean delete(K key) {
        boolean deleted = false;
        int hash = key.hashCode();
        int index = hash % this.container.length;
        Entry<K, V> target = this.container[index];
        if (target != null && target.hash == hash
                && target.key.equals(key)) {
            this.container[index] = null;
            deleted = true;
            this.size--;
            this.modCount++;
        }
        return deleted;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            int expectedModCount = modCount;
            int position;
            int nextPosition;

            public boolean setNextPosition() {
                this.nextPosition = -1;
                for (int i = this.position; i < container.length; i++) {
                    if (container[i] != null) {
                        this.nextPosition = i;
                        break;
                    }
                }
                return this.nextPosition >= 0;
            }

            @Override
            public boolean hasNext() {
                return this.setNextPosition();
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                K result = container[nextPosition].key;
                this.position = this.nextPosition + 1;
                return result;
            }
        };
    }

    /**
     * Запись отображения
     * @param <K> параметр типа ключа
     * @param <V> параметр типа значения
     */
    class Entry<K, V> {
        K key;
        V value;
        int hash;
        Entry(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }


}
