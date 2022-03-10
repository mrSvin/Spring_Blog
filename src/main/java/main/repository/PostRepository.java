package main.repository;

import main.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "order by time " +
            "limit ?1 offset ?2", nativeQuery = true)
    public List<Post> findByEarly(int limit, int offset);

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "order by time desc " +
            "limit ?1 offset ?2", nativeQuery = true)
    public List<Post> findByRecent(int limit, int offset);

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "INNER join skillbox_blog.post_votes ON skillbox_blog.post_votes.post_id = skillbox_blog.posts.id " +
            "order by value desc " +
            "limit ?1 offset ?2", nativeQuery = true)
    public List<Post> findByBest(int limit, int offset);

    @Query(value="SELECT *, COUNT(post_id) as 'count_comments' FROM skillbox_blog.post_comments" +
            " join skillbox_blog.posts on skillbox_blog.posts.id = skillbox_blog.post_comments.post_id " +
            "group by post_id " +
            "order by count_comments desc " +
            "limit 10 offset 0", nativeQuery = true)
    public List<Post> findByPopular(int limit, int offset);

}

