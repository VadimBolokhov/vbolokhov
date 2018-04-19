package ru.job4j.set;

/**
 * Хэш-множество
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 * @param <E>
 */
public class SimpleHashSet<E> {
    /** Хранилище элементов коллекции */
    private Object[] hashArray;
    /** Размер массива по умолчанию */
    private static final int DEFAULT_LENGTH = 10;
    /** Количество заполненных ячеек */
    private int size = 0;

    /**
     * Конструктор - создание множества
     */
    public SimpleHashSet() {
        this(DEFAULT_LENGTH);
    }

    /**
     * Конструктор - создание множества с заданным параметром
     * @param length - начальная длина массива
     */
    public SimpleHashSet(int length) {
        this.hashArray = new Object[length];
    }

    /**
     * Добавляет элемент в множество
     * @param item добавляемый элемент
     * @return true - если добавляемый элемент не содердится в коллекции
     */
    public boolean add(E item) {
        boolean added = false;
        if (!this.contains(item)) {
            if (this.hashArray.length == this.size) {
                this.resize();
            }
            int index = this.indexFor(item, this.hashArray.length);
            this.hashArray[index] = item;
            this.size++;
            added = true;
        }
        return added;
    }

    /**
     * Вычисляет номер ячейки массива
     * @param item заданный элемент
     * @param length длина массива
     * @return номер ячейки
     */
    private int indexFor(E item, int length) {
        return Math.abs(item.hashCode()) % length;
    }

    /**
     * Увеличивает размер массива при заполнении
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        int newLength = this.hashArray.length == 0 ? DEFAULT_LENGTH
                : 2 * this.hashArray.length;
        Object[] newArray = new Object[newLength];
        for (Object item : this.hashArray) {
            if (item != null) {
                int index = this.indexFor((E) item, newArray.length);
                newArray[index] = item;
            }
        }
        this.hashArray = newArray;
    }

    /**
     * Проверяет содержит ли множество заданный элемент
     * @param item заданный элемент
     * @return true - если элемент найден, false - если не найден
     */
    public boolean contains(E item) {
        boolean result = false;
        if (this.hashArray.length != 0) {
            int index = this.indexFor(item, this.hashArray.length);
            result = index < this.hashArray.length && item.equals(this.hashArray[index]);
        }
        return result;
    }

    /**
     * Удаление заданного элемента из коллекции
     * @param item заданный элемент
     * @return true - если элемент найден и удален успешно
     */
    public boolean remove(E item) {
        boolean removed = false;
        int index = this.indexFor(item, this.hashArray.length);
        if (item.equals(this.hashArray[index])) {
            this.hashArray[index] = null;
            this.size--;
            removed = true;
        }
        return removed;
    }
}
