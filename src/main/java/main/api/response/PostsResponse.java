package main.api.response;

import java.util.List;
import java.util.Map;

public class PostsResponse {

    private int counts;

    //    private Map<String, String> posts;
    private List<Map<String, Object>> posts;

    public int getCounts() {
        return counts;
    }
    public void setCounts(int counts) {
        this.counts = counts;
    }

    public List<Map<String, Object>> getPosts() {
        return posts;
    }

    public void setPosts(List<Map<String, Object>> posts) {
        this.posts = posts;
    }


//    public Map<String, String> getPosts() {
//        return posts;
//    }
//    public void setPosts(Map<String, String> posts) {
//        this.posts = posts;
//    }


}
