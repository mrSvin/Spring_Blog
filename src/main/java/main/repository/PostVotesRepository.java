package main.repository;

import main.model.Post;
import main.model.PostVotes;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface PostVotesRepository extends CrudRepository<PostVotes, Integer> {

    @Query(value="SELECT COUNT(value) FROM skillbox_blog.post_votes  where post_id =?1 AND value =?2", nativeQuery = true)
    public Integer findMyPostVotesLikes(int postId, int typeLike);

    @Query(value="SELECT COUNT(value) FROM skillbox_blog.post_votes  where post_id =?1 AND value =?2 AND user_id =?3", nativeQuery = true)
    public Integer findUserVotesLikes(int postId, int typeLike, int userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO `skillbox_blog`.`post_votes` (`time`, `value`, `post_id`, `user_id`) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    public void addLike(@Param("time") Date time, @Param("likeValue") Integer likeValue, @Param("postId") Integer postId, @Param("userId") Integer userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `skillbox_blog`.`post_votes` SET `value` = ?1 WHERE (`post_id` = ?2 and `user_id` = ?3)", nativeQuery = true)
    public void changeLike(@Param("likeValue") Integer likeValue, @Param("postId") Integer postId, @Param("userId") Integer userId);

    @Query(value="SELECT count(*) FROM skillbox_blog.post_votes where value = 1 and user_id = ?1", nativeQuery = true)
    public Integer statisticLikesCount(int userId);

    @Query(value="SELECT count(*) FROM skillbox_blog.post_votes where value = -1 and user_id = ?1", nativeQuery = true)
    public Integer statisticDislikesCount(int userId);

    @Query(value="SELECT count(*) FROM skillbox_blog.post_votes where value = 1", nativeQuery = true)
    public Integer statisticAllLikesCount();

    @Query(value="SELECT count(*) FROM skillbox_blog.post_votes where value = -1", nativeQuery = true)
    public Integer statisticAllDislikesCount();

}

