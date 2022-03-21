package main.repository;

import main.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Integer> {

    @Query(value="SELECT email FROM skillbox_blog.users where email = ?1", nativeQuery = true)
    public List<String> findByEmail(String email);

}