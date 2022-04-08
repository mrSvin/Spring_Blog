package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingsRequest {
    @JsonProperty("MULTIUSER_MODE")
    private Boolean multiUserMode;
    @JsonProperty("POST_PREMODERATION")
    private Boolean postPremoderation;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    private Boolean statisticsIsPublic;

    public Boolean getMultiUserMode() {
        return multiUserMode;
    }

    public void setMultiUserMode(Boolean multiUserMode) {
        this.multiUserMode = multiUserMode;
    }

    public Boolean getPostPremoderation() {
        return postPremoderation;
    }

    public void setPostPremoderation(Boolean postPremoderation) {
        this.postPremoderation = postPremoderation;
    }

    public Boolean getStatisticsIsPublic() {
        return statisticsIsPublic;
    }

    public void setStatisticsIsPublic(Boolean statisticsIsPublic) {
        this.statisticsIsPublic = statisticsIsPublic;
    }
}
