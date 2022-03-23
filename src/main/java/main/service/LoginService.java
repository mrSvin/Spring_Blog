package main.service;

import main.api.response.LoginResponse;
import main.api.response.LogoutResponse;
import main.repository.PostRepository;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class LoginService {

    public static Map<String, Integer> sessions = new HashMap<>();
    private int userId = 0;

    private final UsersRepository usersRepository;
    private final PostRepository postRepository;

    public LoginService(UsersRepository usersRepository, PostRepository postRepository) {
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
    }

    public LoginResponse login(String email, String password, String authSession) {

        LoginResponse loginResponse = new LoginResponse();

        if (usersRepository.findAuth(email, password).size() > 0) {
            userId = usersRepository.findAuth(email, password).get(0);

            sessions.put(authSession, usersRepository.findUserInfo(userId).getId());

            loginResponse.setResult(true);

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", usersRepository.findUserInfo(userId).getId());
            userInfo.put("name", usersRepository.findUserInfo(userId).getName());
            userInfo.put("photo", usersRepository.findUserInfo(userId).getPhoto());
            userInfo.put("email", usersRepository.findUserInfo(userId).getEmail());
            if (usersRepository.findUserInfo(userId).getIs_moderator() == 1) {
                userInfo.put("moderation", true);
                userInfo.put("settings", true);
                userInfo.put("moderationCount", postRepository.findByModerationStatus());
            } else {
                userInfo.put("moderation", false);
                userInfo.put("settings", false);
                userInfo.put("moderationCount", 0);
            }

            loginResponse.setUser(userInfo);
        } else {
            loginResponse.setResult(false);
        }

        return loginResponse;
    }

    public LogoutResponse logout(String authCoocie) {
        LogoutResponse logoutResponse = new LogoutResponse();
        sessions.remove(authCoocie);
        logoutResponse.setResult(true);
        return logoutResponse;
    }
}
