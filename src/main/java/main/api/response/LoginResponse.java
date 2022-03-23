package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class LoginResponse {
    private boolean result;
    private Map<String, Object> user;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }

}
