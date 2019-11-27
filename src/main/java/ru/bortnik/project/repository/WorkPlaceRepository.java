package ru.bortnik.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bortnik.project.entity.FullName;
import ru.bortnik.project.entity.WorkPlace;

@Repository
public interface WorkPlaceRepository extends JpaRepository<WorkPlace, FullName> {
}
