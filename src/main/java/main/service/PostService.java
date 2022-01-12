package main.service;

import main.api.response.AuthCheckResponse;
import main.api.response.PostsResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PostService {
    public PostsResponse getPosts() {

        PostsResponse postsResponse = new PostsResponse();

        Map<String, Integer> map = new HashMap<String, Integer>() {};

        map.put("id", 1);
        map.put("timestamp", 1592338706); // want to pass Lists, Integers also

        postsResponse.setPosts(map);

        return postsResponse;
    }
}
