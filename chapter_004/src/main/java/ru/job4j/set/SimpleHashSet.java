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
            int index = item.hashCode() % this.hashArray.length;
            this.hashArray[index] = item;
            this.size++;
            added = true;
        }
        return added;
    }

    /**
     * Увеличивает размер массива при заполнении
     */
    private void resize() {
        int newLength = this.hashArray.length == 0 ? DEFAULT_LENGTH
                : 2 * this.hashArray.length;
        Object[] newArray = new Object[newLength];
        for (int i = 0; i < this.hashArray.length; i++) {
            if (this.hashArray[i] != null) {
                int index = this.hashArray[i].hashCode() % newArray.length;
                newArray[index] = this.hashArray[i];
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
        boolean contains = false;
        for (Object object : this.hashArray) {
            if (object != null && ((E) object).equals(item)) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /**
     * Удаление заданного элемента из коллекции
     * @param item заданный элемент
     * @return true - если элемент найден и удален успешно
     */
    public boolean remove(E item) {
        boolean removed = false;
        for (int i = 0; i < this.hashArray.length; i++) {
            if (item.equals(this.hashArray[i])) {
                this.hashArray[i] = null;
                removed = true;
                break;
            }
        }
        return removed;
    }
}
