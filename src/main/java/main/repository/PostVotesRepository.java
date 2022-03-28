package main.repository;

import main.model.Post;
import main.model.PostVotes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostVotesRepository extends CrudRepository<PostVotes, Integer> {

    @Query(value="SELECT value FROM skillbox_blog.post_votes where post_id=?1", nativeQuery = true)
    public Integer findMyPostVotesValue(int postId);

}

