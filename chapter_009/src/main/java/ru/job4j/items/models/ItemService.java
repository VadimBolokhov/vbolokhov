package ru.job4j.items.models;

import java.util.List;

/**
 * Items validation service
 * @author Vadim Bolokhov
 */
public enum ItemService implements Service {

    INSTANCE;
    /** The storage */
    private final Persistence store = DBStore.getInstance();

    public static Service getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Item> getAllItems() {
        return this.store.getAllItems();
    }

    @Override
    public List<Item> getUndoneItems() {
        return this.store.getUndoneItems();
    }

    @Override
    public Item addItem(Item item) {
        return this.store.addItem(item);
    }

    @Override
    public boolean update(Item item) {
        return this.store.update(item);
    }
}
