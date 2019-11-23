package ru.bortnik.second.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bortnik.second.dto.SubscriberDto;
import ru.bortnik.second.exception.SubscriberException;
import ru.bortnik.second.storage.SubscriberStorage;

import javax.annotation.PreDestroy;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriberService {

    private final SubscriberStorage subscriberStorage;

    private boolean added = false;

    public synchronized void add(SubscriberDto subscriberDto) {
        if (added) {
            log.error("Try to add another subscriber");
            throw new SubscriberException("Can't add a subscriber. Send existent subscriber or clear first");
        }

        log.info("Add subscriber to storage");
        subscriberStorage.add(subscriberDto);
        added = true;
    }

    public synchronized void send() {
        if (!added) {
            log.error("Try to commit empty storage");
            throw new SubscriberException("Can't commit. Add subscriber first");
        }

        log.info("Commit storage");
        subscriberStorage.commit();
        added = false;
    }

    @PreDestroy
    public synchronized void clear() {
        log.info("Clear storage");
        subscriberStorage.clear();
        added = false;
    }
}
