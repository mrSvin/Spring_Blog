package main.api.response;

import java.util.List;
import java.util.Map;

public class TagResponse {
    private List<Map<String, Object>> tags;

    public List<Map<String, Object>> getTags() {
        return tags;
    }

    public void setTags(List<Map<String, Object>> tags) {
        this.tags = tags;
    }

}
