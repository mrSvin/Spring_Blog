package main.repository;

import main.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value="SELECT * FROM posts " +
            "where moderation_status='ACCEPTED'" +
            "order by time " +
            "limit ?1 offset ?2", nativeQuery = true)
    List<Post> findByEarly(int limit, int offset);

    @Query(value="SELECT * FROM posts " +
            "where moderation_status='ACCEPTED'" +
            "order by time desc " +
            "limit ?1 offset ?2", nativeQuery = true)
    List<Post> findByRecent(int limit, int offset);

    @Query(value="SELECT * FROM posts " +
            "INNER join post_votes ON post_votes.post_id = posts.id " +
            "where moderation_status='ACCEPTED'" +
            "order by value desc " +
            "limit ?1 offset ?2", nativeQuery = true)
    List<Post> findByBest(int limit, int offset);

    @Query(value="SELECT *, COUNT(post_id) as 'count_comments' FROM post_comments" +
            " join posts on posts.id = post_comments.post_id " +
            "where moderation_status='ACCEPTED'" +
            "group by post_id " +
            "order by count_comments desc " +
            "limit ?1 offset ?2", nativeQuery = true)
    List<Post> findByPopular(int limit, int offset);

    @Query(value="SELECT * FROM posts " +
            "where text LIKE ?1 " +
            "limit ?2 offset ?3", nativeQuery = true)
    public List<Post> findQuery(String query, int limit, int offset);

    @Query(value="SELECT date(time) FROM posts " +
            "where year(time)=?1 AND is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "group by date(time) " +
            "ORDER BY MIN(time)", nativeQuery = true)
    List<String> findYear(int year);

    @Query(value="SELECT count(date(time)) FROM posts " +
            "where year(time)=?1 AND is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "group by date(time) " +
            "ORDER BY MIN(time)", nativeQuery = true)
    List<Integer> findYearCount(int year);

    @Query(value="SELECT * FROM posts " +
            "where date(time)=?1 AND is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "limit ?2 offset ?3", nativeQuery = true)
    List<Post> findByDate(String date, int limit, int offset);

    @Query(value="SELECT * FROM posts " +
            "where id = (SELECT post_id FROM tag2post" +
            " where id = (select id FROM tags where name = ?1)) " +
            "limit ?2 offset ?3", nativeQuery = true)
    List<Post> findByTag(String tag, int limit, int offset);

    @Query(value="SELECT * FROM posts " +
            "where is_active = 1 AND moderation_status = ?1 " +
            "limit ?2 offset ?3", nativeQuery = true)
    List<Post> findByStatus(String status, int limit, int offset);

    @Query(value="SELECT COUNT(*) FROM posts where moderation_status = 'NEW'", nativeQuery = true)
    Integer findByModerationStatus();

    @Query(value="SELECT * FROM posts " +
            "where moderation_status = ?1 AND id=?2 AND is_active = ?3 " +
            "limit ?4 offset ?5 ", nativeQuery = true)
    List<Post> findMyPost(String status, int id, int isActive, int limit, int offset);

    @Query(value="SELECT * FROM posts " +
            "where is_active = 0  AND id=?1 " +
            "limit ?2 offset ?3 ", nativeQuery = true)
    List<Post> findMyPostByInactive(int id, int limit, int offset);

    @Modifying
    @Transactional
    @Query("update posts p set p.text = :text, p.is_active = :isActive, p.time = :time, p.title = :title, p.moderation_status = 'NEW' where p.id = :id")
    void editPostUser(@Param("id") int postId, @Param("isActive") int isActive, @Param("text") String text, @Param("time") Date time, @Param("title") String title);

    @Modifying
    @Transactional
    @Query("update posts p set p.text = :text, p.is_active = :isActive, p.time = :time, p.title = :title where p.id = :id")
    void editPostModerator(@Param("id") int postId, int isActive, @Param("text") String text, @Param("time") Date time, @Param("title") String title);

    @Query(value="SELECT * FROM posts " +
            "where id=?1", nativeQuery = true)
    List<Post> findPostId(int idPost);

    @Query(value="SELECT year(time) FROM posts group by year(time)", nativeQuery = true)
    List<Integer> findYearsForCalendar();

    @Query(value="SELECT COUNT(*) FROM posts where user_id = ?1", nativeQuery = true)
    Integer countMyPosts(int idUser);

    @Query(value="SELECT COUNT(*) FROM posts where user_id = ?1 and id = ?2", nativeQuery = true)
    Integer countMyPostsId(int idUser, int idPost);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `posts` SET `view_count` = `view_count`+1 WHERE (`id` = ?1)", nativeQuery = true)
    void addView(@Param("postId") Integer postId);

    @Query(value="SELECT count(view_count) FROM posts where user_id = ?1", nativeQuery = true)
    Integer countViewsMyPosts(int idUser);

    @Query(value="SELECT min(time) FROM posts where user_id = ?1", nativeQuery = true)
    public Date firstMyPost(int idUser);

    @Query(value="SELECT COUNT(*) FROM posts", nativeQuery = true)
    public Integer countAllPosts();

    @Query(value="SELECT count(view_count) FROM posts", nativeQuery = true)
    Integer countViewsAllPosts();

    @Query(value="SELECT min(time) FROM posts", nativeQuery = true)
    Date firstPost();

    @Modifying
    @Transactional
    @Query(value = "UPDATE `posts` SET `moderation_status` = ?1 WHERE (`id` = ?2)", nativeQuery = true)
    void changeStatus(@Param("status") String status, @Param("postId") Integer postId);

}

