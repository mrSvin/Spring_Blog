package main.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Global_settingsRepository extends CrudRepository<Global_settings, Integer> {
    Iterable<Global_settings> findAllById(int i);

}