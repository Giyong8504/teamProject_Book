package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamproject.entities.Status;

public interface StatusRepository extends JpaRepository<Status,Long> {
}
