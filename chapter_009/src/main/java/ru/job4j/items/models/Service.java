package ru.job4j.items.models;

import java.util.List;

/**
 * Items validation service interface.
 * @author Vadim Bolokhov
 */
public interface Service {

    /**
     * Returns a list of all items
     * @return items list
     */
    List<Item> getAllItems();

    /**
     * Returns a list of items that have {@code done = false} field value only
     * @return items list
     */
    List<Item> getUndoneItems();

    /**
     * Add a new item to the store
     * @param item Item to add
     * @return item with new id
     */
    Item addItem(Item item);

    /**
     * Updates the item in the store
     * @param item Item to update
     * @return true if the item updated successfully
     */
    boolean update(Item item);
}
