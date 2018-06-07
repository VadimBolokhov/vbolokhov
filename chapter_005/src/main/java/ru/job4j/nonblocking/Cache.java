package ru.job4j.nonblocking;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Кэш для хранения моделей.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Cache {
    /** Хранилище моделей */
    private ConcurrentHashMap<Integer, Base> cache;

    /**
     * Конструктор - создание объекта
     */
    public Cache() {
        this.cache = new ConcurrentHashMap<>();
    }

    /**
     * Добавляет модель в хранилище
     * @param model добавляемая модель
     */
    public void add(Base model) {
        this.cache.putIfAbsent(model.getId(), model);
    }

    /**
     * Обновление модели
     * @param model обновленная модель
     */
    public void update(Base model) {
        int key = model.getId();
        this.cache.computeIfPresent(key, (k, v) -> {
            if (this.cache.get(key).getVersion() != model.getVersion()) {
                throw new OptimisticException("Incorrect version");
            }
            model.updateVersion();
            return model;
        });
    }

    /**
     * Удаляет модель из хранилища
     * @param model удаляемая модель
     */
    public void delete(Base model) {
        this.cache.remove(model.getId());
    }

    /**
     * Поиск подели по идентификатору
     * @param id идентификатор
     * @return {@code Optional}, содержащий результат поиска
     */
    public Optional<Base> findById(int id) {
        return Optional.ofNullable(this.cache.get(id));
    }
}
