package main.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Post_commentsRepository extends CrudRepository<Post_comments, Integer> {
}


//@Repository
//public interface Post_votesRepository extends CrudRepository<Post_votes, Integer> {
//}

