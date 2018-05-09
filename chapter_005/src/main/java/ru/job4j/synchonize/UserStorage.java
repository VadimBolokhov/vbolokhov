package ru.job4j.synchonize;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Класс хранилища пользователей.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@ThreadSafe
public class UserStorage {
    /** Хранилище пользователей */
    @GuardedBy("this")
    private Map<Integer, User> users = new HashMap<>();

    /**
     * Добавляет пользователя в хранилище
     * @param user добавляемый пользователь
     * @return {@code true} - если операция выполнена успешно,
     * {@code false} - если пользователь уже существует
     */
    public synchronized boolean add(User user) {
        boolean added = false;
        if (!this.users.containsKey(user.getId())) {
            this.users.put(user.getId(), user);
            added = true;
        }
        return added;
    }

    /**
     * Обновляет запись пользователя
     * @param user пользователь
     * @return {@code true} - если операция выполнена успешно,
     * {@code false} - если пользователь не найден
     */
    public synchronized boolean update(User user) {
        boolean result = false;
        if (this.users.containsKey(user.getId())) {
            this.users.replace(user.getId(), user);
            result = true;
        }
        return result;
    }

    /**
     * Удаляет пользователя из хранилища
     * @param user удаляемый пользователь
     * @return {@code true} - если операция выполнена успешно,
     * {@code false} - если пользователь не найден
     */
    public synchronized boolean delete(User user) {
        boolean deleted = false;
        if (this.users.containsKey(user.getId())) {
            this.users.remove(user.getId());
            deleted = true;
        }
        return deleted;
    }

    /**
     * Перевод денег с одного счёта на другой
     * @param fromId идентификатор отправителя перевода
     * @param toId идентификатор получателя перевода
     * @param amount сумма перевода
     * @return {@code true} - если операция выполнена успешно,
     * {@code false} - в противном случае
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (this.correctOperation(fromId, toId, amount)) {
            this.users.get(fromId).addValue(-amount);
            this.users.get(toId).addValue(amount);
            result = true;
        }
        return result;
    }

    /**
     * Проверяет, возможна ли операция перевода
     * @param fromId идентификатор отправителя перевода
     * @param toId идентификатор получателя перевода
     * @param amount сумма перевода
     * @return {@code true} - если на счету отправителья достаточно средств,
     * {@code false} - если перевод не возможен
     */
    private synchronized boolean correctOperation(int fromId, int toId, int amount) {
        return amount > 0 && this.users.containsKey(fromId)
                && this.users.containsKey(toId)
                && this.users.get(fromId).getAmount() >= amount;
    }

    /**
     * Поиск пользователя по идентификатору
     * @param id идентификатор
     * @return результат поиска
     */
    public Optional<User> findById(int id) {
        Optional<User> result = Optional.ofNullable(this.users.get(id));
        return result;
    }
}
