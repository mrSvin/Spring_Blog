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
            "limit ?1 offset ?2", nativeQuery = true)
    public List<Post> findByPopular(int limit, int offset);

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "where text LIKE ?1 " +
            "limit ?2 offset ?3", nativeQuery = true)
    public List<Post> findQuery(String query, int limit, int offset);

    @Query(value="SELECT date(time) FROM skillbox_blog.posts " +
            "where year(time)=?1 AND is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "group by date(time) " +
            "order by time", nativeQuery = true)
    public List<String> findYear(int year);

    @Query(value="SELECT count(date(time)) FROM skillbox_blog.posts " +
            "where year(time)=?1 AND is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "group by date(time) " +
            "order by time", nativeQuery = true)
    public List<Integer> findYearCount(int year);

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "where date(time)=?1 AND is_active = 1 AND moderation_status = 'ACCEPTED' " +
            "limit ?2 offset ?3", nativeQuery = true)
    public List<Post> findByDate(String date, int limit, int offset);

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "where id = (SELECT post_id FROM skillbox_blog.tag2post" +
            " where id = (select id FROM skillbox_blog.tags where name = ?1)) " +
            "limit ?2 offset ?3", nativeQuery = true)
    public List<Post> findByTag(String tag, int limit, int offset);

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "where is_active = 1 AND moderation_status = ?1 " +
            "limit ?2 offset ?3", nativeQuery = true)
    public List<Post> findByStatus(String status, int limit, int offset);

    @Query(value="SELECT COUNT(*) FROM skillbox_blog.posts where moderation_status = 'NEW'", nativeQuery = true)
    public Integer findByModerationStatus();

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "where moderation_status = ?1 AND id=?2 AND is_active = ?3 " +
            "limit ?4 offset ?5 ", nativeQuery = true)
    public List<Post> findMyPost(String status, int id, int isActive, int limit, int offset);

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "where is_active = 0  AND id=?1 " +
            "limit ?2 offset ?3 ", nativeQuery = true)
    public List<Post> findMyPostByInactive(int id, int limit, int offset);

    @Modifying
    @Transactional
    @Query("update posts p set p.text = :text, p.is_active = :isActive, p.time = :time, p.title = :title, p.moderation_status = 'NEW' where p.id = :id")
    public void editPostUser(@Param("id") int postId, @Param("isActive") int isActive, @Param("text") String text, @Param("time") Date time, @Param("title") String title);

    @Modifying
    @Transactional
    @Query("update posts p set p.text = :text, p.is_active = :isActive, p.time = :time, p.title = :title where p.id = :id")
    public void editPostModerator(@Param("id") int postId, int isActive, @Param("text") String text, @Param("time") Date time, @Param("title") String title);

    @Query(value="SELECT * FROM skillbox_blog.posts " +
            "where id=?1", nativeQuery = true)
    public List<Post> findPostId(int idPost);

    @Query(value="SELECT  year(time) FROM skillbox_blog.posts group by year(time)", nativeQuery = true)
    public List<Integer> findYearsForCalendar();

}

