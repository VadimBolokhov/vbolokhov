package ru.job4j.bank;

import java.util.*;

/**
 * Банковские переводы.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Bank {
    /** Отображение клиентов банка */
    private Map<User, List<Account>> users;

    /**
     * Конструктор - создание объекта
     */
    public Bank() {
        this.users = new HashMap<>();
    }

    /**
     * Добавляет нового клиента
     * @param user клиент
     */
    public void addUser(User user) {
        this.users.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Удаляет клиента из отображения
     * @param user заданный клиент
     */
    public void deleteUser(User user) {
        this.users.remove(user);
    }

    /**
     * Возвращает множество всех клиентов банка
     * @return множество клиентов
     */
    public Set<User> getUsers() {
        return this.users.keySet();
    }

    /**
     * Создать счёт для клиента
     * @param passport паспортные данные
     * @param account счёт
     */
    public void addAccountToUser(String passport, Account account) {
        for (Map.Entry<User, List<Account>> entry : this.users.entrySet()) {
            if (entry.getKey().getPassport().equals(passport)) {
                entry.getValue().add(account);
                break;
            }
        }
    }

    /**
     * Удаляет счёт клиента
     * @param passport паспортные данные
     * @param account счёт, который требуется удалить
     */
    public void deleteAccountFromUser(String passport, Account account) {
        for (Map.Entry<User, List<Account>> entry: this.users.entrySet()) {
            if (entry.getKey().getPassport().equals(passport)) {
                entry.getValue().remove(account);
                break;
            }
        }
    }

    /**
     * Возвращает список счетов клиента
     * @param passport паспортные данные клиента
     * @return списо счетов
     */
    public List<Account> getUserAccounts(String passport) {
        List<Account> result = new ArrayList<>();
        for (Map.Entry<User, List<Account>> entry : this.users.entrySet()) {
            if (entry.getKey().getPassport().equals(passport)) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }

    /**
     * Переводит деньги с одного счета на другой
     * @param srcPassport паспортные данные отправителья
     * @param srcRequisite номер счета отправителя
     * @param destPassport паспортные данные получателя
     * @param dstRequisite номер счета получаетля
     * @param amount размер перевода
     * @return true - если перевод произведен успешно, false - в противном случае
     */
    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String dstRequisite,
                                 double amount) {
        boolean success = false;
        Optional<Account> source = this.getAccount(srcPassport, srcRequisite);
        Optional<Account> dest = this.getAccount(destPassport, dstRequisite);
        if (source.isPresent() && dest.isPresent()) {
            success = source.get().transfer(dest.get(), amount);
        }
        return success;
    }

    /**
     * Возвращает банковский счёт клиента
     * @param passport паспортные данные клиента
     * @param requisite номер счёта
     * @return банковский счёт
     */
    public Optional<Account> getAccount(String passport, String requisite) {
        Account result = null;
        for (Account acc : this.getUserAccounts(passport)) {
            if (acc.getRequisites().equals(requisite)) {
                result = acc;
                break;
            }
        }
        return Optional.ofNullable(result);
    }
}
