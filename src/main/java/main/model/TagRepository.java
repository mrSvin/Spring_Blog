package main.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tags, Integer> {
    List<Tags> findAllByOrderByIdDesc();
    Tags findByName(String name);

}
