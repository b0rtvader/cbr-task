package ru.bortnik.second.repository;

import ru.bortnik.second.dto.SubscriberDto;

/**
 * Хранилище абонентов
 */
public interface SubscriberStorage {

    /**
     * Добавляет абонента во временное хранилище
     *
     * @param subscriberDto абонент
     */
    void add(SubscriberDto subscriberDto);

    /**
     * Переносит абонентов из временного хранилища в постоянное
     *
     * @return количество перенесенных абонентов
     */
    int commit();

    /**
     * Очищает временное хранилище
     */
    void clear();
}
