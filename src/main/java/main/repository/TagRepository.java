package main.repository;

import main.model.Post;
import main.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    List<Tag> findAllByOrderByIdDesc();

    @Query(value="SELECT id FROM skillbox_blog.tags where name = ?1", nativeQuery = true)
    public String findIdByName(String nameTag);

}
