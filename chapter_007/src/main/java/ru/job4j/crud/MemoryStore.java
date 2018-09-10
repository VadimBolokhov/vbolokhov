package ru.job4j.crud;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User store.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public enum MemoryStore implements Store {
    /** Singleton instance */
    INSTANCE;
    /** User store */
    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void add(User user) {
        String id = user.getId();
        this.users.put(id, user);
    }

    @Override
    public void update(String id, User newUser) {
        String name = newUser.getName();
        String email = newUser.getEmail();
        User user = this.users.get(id);
        if (name != null) {
            user.setName(name);
        }
        if (email != null) {
            user.setEmail(email);
        }
    }

    @Override
    public void delete(String id) {
        this.users.remove(id);
    }

    @Override
    public List<User> findAll() {
        return new LinkedList<>(this.users.values());
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(this.users.get(id));
    }
}
