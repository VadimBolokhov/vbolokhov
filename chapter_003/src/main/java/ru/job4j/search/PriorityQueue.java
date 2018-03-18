package ru.job4j.search;

import java.util.LinkedList;

/**
 * Список задач.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class PriorityQueue {
    private LinkedList<Task> tasks = new LinkedList<>();

    /**
     * Добавляет задачу в очередь в зависимости от приоритета
     * @param task
     */
    public void put(Task task) {
        boolean added = false;
        for (int index = 0; index < this.tasks.size(); index++) {
            if (task.getPriority() < this.tasks.get(index).getPriority()) {
                this.tasks.add(index, task);
                added = true;
                break;
            }
        }
        if (!added) {
            this.tasks.add(task);
        }
    }

    /**
     * Извлекает первую по приоритету задачу из списка
     * @return задача
     */
    public Task take() {
        return this.tasks.poll();
    }
}
