package org.teamproject.commons.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.teamproject.entities.Configs;
import org.teamproject.repositories.ConfigsRepository;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigsRepository repository;

    public void delete(String code) {
        Configs configs = repository.findById(code).orElse(null);
        if (configs == null) {
            return;
        }

        repository.delete(configs);
        repository.flush();
    }
}