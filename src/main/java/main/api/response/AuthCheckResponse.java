package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthCheckResponse {
    @JsonProperty("result")
    private boolean resultCheck;

    public boolean isResult() {
        return resultCheck;
    }

    public void setResult(boolean resultCheck) {
        this.resultCheck = resultCheck;
    }
}
