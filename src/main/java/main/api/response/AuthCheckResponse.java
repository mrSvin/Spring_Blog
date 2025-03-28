package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthCheckResponse {
    @JsonProperty("result")
    private boolean resultCheck;

    @JsonProperty("user")
    private UserAuthResponse user;

    public boolean isResult() {
        return resultCheck;
    }

    public void setResult(boolean resultCheck) {
        this.resultCheck = resultCheck;
    }

    // Геттеры и сеттеры для user
    public UserAuthResponse getUser() {
        return user;
    }

    public void setUser(UserAuthResponse user) {
        this.user = user;
    }

}

