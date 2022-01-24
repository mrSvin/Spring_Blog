package main.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Tag2postRepository extends CrudRepository<Tag2post, Integer> {
    List<Tag2post> findAllByOrderByIdDesc();
    Tag2post findAllById(int idTag);

//    long countTagsByName(int name);

}

//    List<Tags> findAllByOrderByIdDesc();
//    Tags findByName(String name);
