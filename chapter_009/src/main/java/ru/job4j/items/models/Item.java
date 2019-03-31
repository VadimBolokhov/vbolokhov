package ru.job4j.items.models;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Item POJO.
 * @author Vadim Bolokhov
 */
public class Item {
    /** Id */
    private int id;
    /** Description */
    private String desc;
    /** Creation date */
    private Timestamp created;
    /** Boolean flag */
    private boolean done;

    public Item() {
    }

    public Item(String desc, Timestamp created, boolean done) {
        this.desc = desc;
        this.created = created;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return String.format("Item{id=%d, desc='%s', created=%s, done=%s}",
                id, desc, created, done);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id
                && desc.equals(item.desc)
                && created.equals(item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, desc, created);
    }
}
