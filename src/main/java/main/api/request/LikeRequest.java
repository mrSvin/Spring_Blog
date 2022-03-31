package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LikeRequest {
    @JsonProperty("post_id")
    private Integer postId;

    public Integer getPostId() {
        return postId;
    }
}
