package main.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Post_votesRepository extends CrudRepository<Post_votes, Integer> {
}

