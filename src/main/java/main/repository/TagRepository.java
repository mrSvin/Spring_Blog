package main.repository;

import main.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    List<Tag> findAllByOrderByIdDesc();
    Tag findByName(String name);

}
