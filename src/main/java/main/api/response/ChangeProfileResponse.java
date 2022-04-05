package main.api.response;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class ChangeProfileResponse {
    private boolean result;
    private Map<String, String> errors;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
