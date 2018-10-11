package ru.job4j.crud.models;

import java.time.LocalDate;
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
    /** Next id to be generated */
    private int nextId = 1;

    @Override
    public User add(User user) {
        String id = this.generateId();
        user = new User.Builder().id(id).login(user.getLogin()).password(user.getPassword())
                .name(user.getName()).email(user.getEmail()).createDate(LocalDate.now())
                .role(user.getRole()).build();
        this.users.put(id, user);
        return user;
    }

    private String generateId() {
        return String.valueOf(this.nextId++);
    }

    @Override
    public void update(String id, User newUser) {
        String name = newUser.getName();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        Role role = newUser.getRole();
        User oldUser = this.users.get(id);
        User userUpdate = new User.Builder().id(id).createDate(oldUser.getCreateDate())
                .password(password).name(name).email(email).role(role)
                .build();
        this.users.put(id, userUpdate);
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
