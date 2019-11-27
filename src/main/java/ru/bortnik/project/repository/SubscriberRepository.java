package ru.bortnik.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bortnik.project.entity.FullName;
import ru.bortnik.project.entity.Subscriber;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, FullName> {
}
