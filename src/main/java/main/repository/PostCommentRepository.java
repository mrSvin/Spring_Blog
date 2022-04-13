package main.repository;

import main.model.Post;
import main.model.PostComment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {

    @Query(value="SELECT * FROM post_comments where id = ?1", nativeQuery = true)
    public List<PostComment> findCommentId(int idComment);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `post_comments` (`text`, `time`, `user_id`, `post_id`) " +
            "VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    public void addCommentPost(@Param("text") String text, @Param("time") Date time, @Param("userId") Integer userId, @Param("postId") Integer postId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `post_comments` (`text`, `time`, `user_id`, `post_id`, `parent_id`) " +
            "VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    public void addCommentParent(@Param("text") String text, @Param("time") Date time, @Param("userId") Integer userId, @Param("postId") Integer postId,
                                 @Param("parentId") Integer parentId);

    @Query(value="SELECT * FROM post_comments where post_id = ?1", nativeQuery = true)
    public List<PostComment> findCommentByPostId(int idPost);

}
