package main.repository;

import main.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer> {

    @Query(value="SELECT email FROM skillbox_blog.users where email = ?1", nativeQuery = true)
    public List<String> findByEmail(String email);

    @Query(value="SELECT id FROM skillbox_blog.users where email = ?1 and password = ?2", nativeQuery = true)
    public List<Integer> findAuth(String email, String password);

    @Query(value="SELECT * FROM skillbox_blog.users where id = ?1", nativeQuery = true)
    public User findUserInfo(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `skillbox_blog`.`users` SET `email` = ?1, `name` = ?2, `password` = ?3, `photo` = ?4 WHERE (`id` = ?5)", nativeQuery = true)
    public void changeProfile(@Param("email") String email, @Param("name") String name, @Param("password") String password, @Param("photo") String photo, @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `skillbox_blog`.`users` SET `email` = ?1, `name` = ?2, `password` = ?3 WHERE (`id` = ?4)", nativeQuery = true)
    public void changeProfileNoPhoto(@Param("email") String email, @Param("name") String name, @Param("password") String password, @Param("userId") int userId);

    @Query(value="SELECT email FROM skillbox_blog.users where email = ?1 and id != ?2", nativeQuery = true)
    public List<String> findByEmailAndId(String email, int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE `skillbox_blog`.`users` SET `code` = ?1 WHERE (`email` = ?2);", nativeQuery = true)
    public void changeCode(@Param("code") String code, @Param("email") String email);

}