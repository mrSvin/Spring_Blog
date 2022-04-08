package main.service;

import main.api.response.AuthCheckResponse;
import main.repository.PostRepository;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class ModerationService {

    private final UsersRepository usersRepository;
    private final PostRepository postRepository;

    public ModerationService(UsersRepository usersRepository, PostRepository postRepository) {
        this.usersRepository = usersRepository;
        this.postRepository = postRepository;
    }

    public AuthCheckResponse decisionModeration(String authCoocie, int postId, String decision) {
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();

        int id = LoginService.sessions.get(authCoocie);
        if (usersRepository.findUserInfo(id).getIs_moderator() == 0) {
            authCheckResponse.setResult(false);
        } else if (decision.equals("accept")){
            postRepository.changeStatus("ACCEPTED", postId);
            authCheckResponse.setResult(true);
        } else if (decision.equals("decline")) {
            authCheckResponse.setResult(true);
            postRepository.changeStatus("DECLINED", postId);
        } else {
            authCheckResponse.setResult(false);
        }

        return authCheckResponse;
    }
}
