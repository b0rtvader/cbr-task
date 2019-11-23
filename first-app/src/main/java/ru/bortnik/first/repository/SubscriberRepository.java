package ru.bortnik.first.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bortnik.first.entity.FullName;
import ru.bortnik.first.entity.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, FullName> {
}
