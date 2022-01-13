package main.api.response;

import main.model.Posts;

public class PostsResponse {

    private int counts;

    //    private Map<String, String> posts;
    private Iterable<Posts> posts;

    public int getCounts() {
        return counts;
    }
    public void setCounts(int counts) {
        this.counts = counts;
    }

    public Iterable<Posts> getPosts() {
        return posts;
    }

    public void setPosts(Iterable<Posts> posts) {
        this.posts = posts;
    }


//    public Map<String, String> getPosts() {
//        return posts;
//    }
//    public void setPosts(Map<String, String> posts) {
//        this.posts = posts;
//    }


}
