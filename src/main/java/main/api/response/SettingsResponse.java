package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SettingsResponse {

    @JsonProperty("MULTIUSER_MODE")
    private boolean multiserMode;
    @JsonProperty("POST_PREMODERATION")
    private boolean postPremoderation;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    private boolean statisticIsPublic;

    public SettingsResponse() {
    }

    public boolean isMultiserMode() {
        return multiserMode;
    }

    public void setMultiserMode(boolean multiserMode) {
        this.multiserMode = multiserMode;
    }

    public boolean isPostPremoderation() {
        return postPremoderation;
    }

    public void setPostPremoderation(boolean postPremoderation) {
        this.postPremoderation = postPremoderation;
    }

    public boolean isStatisticIsPublic() {
        return statisticIsPublic;
    }

    public void setStatisticIsPublic(boolean statisticIsPublic) {
        this.statisticIsPublic = statisticIsPublic;
    }

}


//{
//        "MULTIUSER_MODE": false,
//        "POST_PREMODERATION": true,
//        "STATISTICS_IS_PUBLIC": true
//        }