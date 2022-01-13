package main.service;

import main.api.response.PostsResponse;
import main.model.PostRepository;
import main.model.Posts;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostsResponse getPosts(int offset, int limit, String mode) {

        PostsResponse postsResponse = new PostsResponse();

        int count = (int) postRepository.count();
        postsResponse.setCounts(count);

        if (count>0) {
            postsResponse.setPosts(postRepository.findAll());
        } else {
            postsResponse.setPosts(new ArrayList<>());
        }

//        Map<String, Integer> map = new HashMap<String, Integer>() {};

//        map.put("id", offset);
//        map.put("timestamp", limit); // want to pass Lists, Integers also
//

        return postsResponse;
    }



}
