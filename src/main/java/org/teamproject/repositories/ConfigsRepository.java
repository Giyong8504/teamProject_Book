package org.teamproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.teamproject.entities.Configs;

public interface ConfigsRepository extends JpaRepository<Configs, String> {
}