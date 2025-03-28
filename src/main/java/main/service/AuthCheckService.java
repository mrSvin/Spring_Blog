package main.service;

import main.api.response.AuthCheckResponse;
import main.api.response.UserAuthResponse;
import main.model.User;
import main.repository.PostRepository;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthCheckService {

    private final UsersRepository usersRepository;
    private final PostRepository postRepository;

    public AuthCheckService(UsersRepository usersRepository, PostRepository postRepository) {
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
    }

    public AuthCheckResponse getAuthCheck(String authСookie) {

        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        UserAuthResponse userAuthResponse = new UserAuthResponse();

         if (LoginService.sessions.get(authСookie) == null) {
             authCheckResponse.setResult(false);
             return authCheckResponse;
         }

        int userId = LoginService.sessions.get(authСookie);
        User userInfo = usersRepository.findUserInfo(userId);
        userAuthResponse.setId(userInfo.getId());
        userAuthResponse.setName(userInfo.getName());
        userAuthResponse.setPhoto(userInfo.getPhoto());
        userAuthResponse.setEmail(userInfo.getEmail());
        if (userInfo.getIs_moderator() == 1) {
            userAuthResponse.setModeration(true);
            userAuthResponse.setModerationCount(postRepository.findByModerationStatus());
            userAuthResponse.setSettings(true);
        } else {
            userAuthResponse.setModeration(false);
            userAuthResponse.setSettings(false);
        }

        authCheckResponse.setUser(userAuthResponse);
        authCheckResponse.setResult(true);

        return authCheckResponse;
    }
}
