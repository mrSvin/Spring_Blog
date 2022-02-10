package main.repository;

import main.model.GlobalSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSetting, Integer> {
    Iterable<GlobalSetting> findAllById(int i);

}