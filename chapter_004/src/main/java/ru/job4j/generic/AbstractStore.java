package ru.job4j.generic;

/**
 * Класс хранилища.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public abstract class AbstractStore<T extends Base> implements Store<T> {
    private SimpleArray<T> container;

    /**
     * Конструктор - создание хранилища заданного размера
     * @param size количество элементов
     */
    public AbstractStore(int size) {
        this.container = new SimpleArray<>(size);
    }

    /**
     * Возвращает номер ячейки, содержащий элемент с заданным {@code id}
     * @param id идентификатор
     * @return номер ячейки, или -1 если элемент не найден
     */
    private int indexOf(String id) {
        int index = -1;
        for (int i = 0; i < this.container.size(); i++) {
            if (this.container.get(i) != null && this.container.get(i).getId().equals(id)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void add(T model) {
        this.container.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        boolean replaced = false;
        int index = this.indexOf(id);
        if (index >= 0) {
            this.container.set(index, model);
            replaced = true;
        }
        return replaced;
    }

    @Override
    public boolean delete(String id) {
        boolean deleted = false;
        int index = this.indexOf(id);
        if (index >= 0) {
            this.container.delete(index);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public T findById(String id) {
        T result = null;
        int index = this.indexOf(id);
        if (index >= 0) {
            result = this.container.get(index);
        }
        return result;
    }
}
