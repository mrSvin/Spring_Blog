package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class RegisterResponse {

    private Boolean result;
    private Map<String, String> errors;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
