package main.service;

import main.api.response.ChangeProfileResponse;
import main.api.response.LogoutResponse;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RestorePasswordService {

    private final UsersRepository usersRepository;
    private Sender sslSender = new Sender("blogobotik@gmail.com", "test1122");

    public RestorePasswordService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public LogoutResponse restorePassword(String toEmail) {
        LogoutResponse logoutResponse = new LogoutResponse();

        if (usersRepository.findByEmail(toEmail).size()>0) {
            logoutResponse.setResult(true);
            String hash = generateHash();
            String responseMessage = "localhost:8081/login/change-password/" + hash;
            sslSender.send("Восстановление пароля Blog", responseMessage, toEmail);
            usersRepository.changeCode(hash, toEmail);
        } else {
            logoutResponse.setResult(false);
        }

        return logoutResponse;
    }

    private String generateHash() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        return uuid;
    }

    public ChangeProfileResponse changePassword(String code, String password, String captcha, String captchaSecret) {
        ChangeProfileResponse changeProfileResponse = new ChangeProfileResponse();

        changeProfileResponse.setResult(false);

        return changeProfileResponse;
    }
}
