package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentAddRequest {
    @JsonProperty("parent_id")
    private String parentId;
    @JsonProperty("post_id")
    private Integer postId;
    private String text;

    public String getParentId() {
        return parentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }
}
