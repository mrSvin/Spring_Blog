package main.repository;

import main.model.PostComment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment, Integer> {
}


//@Repository
//public interface Post_votesRepository extends CrudRepository<Post_votes, Integer> {
//}

