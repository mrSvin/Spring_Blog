package main.api.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostCalendarDto {

    private Integer years;
    private Map<String, Integer> posts;

    public Map<String, Integer> getPosts() {
        return posts;
    }

    public void setPosts(Map<String, Integer> posts) {
        this.posts = posts;
    }

    public Integer getYears() {
        return years;
    }
    public void setYears(Integer years) {
        this.years = years;
    }

}
