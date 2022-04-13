package main.repository;

import main.model.Post;
import main.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    List<Tag> findAllByOrderByIdDesc();

    @Query(value="SELECT id FROM tags where name = ?1", nativeQuery = true)
    public Integer findIdByName(String nameTag);

    @Query(value="SELECT id FROM tags where name = ?1", nativeQuery = true)
    public List<Integer> findTag(String nameTag);

    @Query(value="SELECT name FROM tags where id = ?1", nativeQuery = true)
    public String findTagsById(Integer id);

}
