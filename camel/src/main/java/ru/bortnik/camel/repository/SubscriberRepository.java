package ru.bortnik.camel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bortnik.camel.entity.FullName;
import ru.bortnik.camel.entity.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, FullName> {
}
