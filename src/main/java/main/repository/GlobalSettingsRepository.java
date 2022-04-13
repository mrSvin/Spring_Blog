package main.repository;

import main.model.GlobalSetting;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSetting, Integer> {
    Iterable<GlobalSetting> findAllById(int i);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `global_settings` SET `value` = ?1 WHERE (`id` = ?2)", nativeQuery = true)
    public void newSettings(@Param("value") String value, @Param("id") Integer id);

}