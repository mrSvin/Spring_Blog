package main.service;

import main.api.response.AuthCheckResponse;
import main.api.response.SettingsResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthCheckService {
    public AuthCheckResponse getAuthCheck() {

        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        authCheckResponse.setResult(true);

        return authCheckResponse;
    }
}
