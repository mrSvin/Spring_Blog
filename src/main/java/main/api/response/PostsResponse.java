package main.api.response;

import java.util.List;
import java.util.Map;

public class PostsResponse {

    private int counts;
    private List<PostDetailsDto> posts;

    public int getCounts() {
        return counts;
    }
    public void setCounts(int counts) {
        this.counts = counts;
    }

    public List<PostDetailsDto> getPosts() {
        return posts;
    }
    public void setPosts(List<PostDetailsDto> posts) {
        this.posts = posts;
    }

}