package ru.job4j.generic;

/**
 * Интерфейс хранилища.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public interface Store<T extends Base> {
    /**
     * Добавление элемента в коллекцию
     * @param model элемент
     */
    void add(T model);

    /**
     * Замена элемента коллекции по заданному {@code id}
     * @param id идентификатор
     * @param model новый элемент
     * @return true - если замена произведена успешно,
     * false - если замена не состоялась
     */
    boolean replace(String id, T model);

    /**
     * Удаление элемента коллекции с заданным {@code id}
     * @param id идентификатор
     * @return true - если элемент удален успешно,
     * false - если элемент не был удален
     */
    boolean delete(String id);

    /**
     * Поиск элемента коллекции по заданному {@code id}
     * @param id идентификатор
     * @return искомый элемент
     */
    T findById(String id);
}
