package main.service;

import main.api.response.PostsResponse;
import main.api.response.TagResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TagService {

    public TagResponse getTags() {

        TagResponse tagResponse = new TagResponse();

        Map<String, String> map = new HashMap<String, String>() {};

        map.put("name", "Java");
        map.put("weight", "1"); // want to pass Lists, Integers also

        tagResponse.setTags(map);

        return tagResponse;
    }


}
