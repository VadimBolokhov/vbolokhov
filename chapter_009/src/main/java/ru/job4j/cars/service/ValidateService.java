package ru.job4j.cars.service;

import ru.job4j.cars.dao.HibernateUserDao;
import ru.job4j.cars.dao.UserDao;
import ru.job4j.cars.models.User;

import java.util.List;
import java.util.Optional;

/**
 * An implementation of Validate interface.
 * @author Vadim Bolokhov
 */
public enum  ValidateService implements Validate {

    INSTANCE;

    private final UserDao dao = HibernateUserDao.INSTANCE;

    private final Object lock = new Object();

    @Override
    public boolean add(User user) {
        boolean result = false;
        synchronized (this.lock) {
            if (this.isLoginAbsent(user.getLogin())
                    && this.dao.add(user) > 0) {
                result = true;
            }
        }
        return result;
    }

    private boolean isLoginAbsent(String login) {
        List<User> users = this.dao.getAll();
        return users.stream()
                .noneMatch(u -> u.getLogin().equals(login));
    }

    @Override
    public boolean update(User user) {
        boolean success = false;
        synchronized (this.lock) {
            Optional<User> userOpt = this.dao.getById(user.getId());
            if (userOpt.isPresent()) {
                User current = userOpt.get();
                user.setPassword(current.getPassword());
                success = this.dao.update(user);
            }
        }
        return success;
    }

    @Override
    public boolean setNewPassword(int id, String oldPwd, String newPwd) {
        boolean result = false;
        synchronized (this.lock) {
            Optional<User> userOpt = this.dao.getById(id);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.getPassword().equals(oldPwd)) {
                    user.setPassword(newPwd);
                    this.dao.update(user);
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        return this.dao.getAll();
    }

    @Override
    public Optional<User> findById(int id) {
        return this.dao.getById(id);
    }
}
