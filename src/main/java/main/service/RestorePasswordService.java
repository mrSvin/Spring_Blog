package main.service;

import main.api.response.ChangeProfileResponse;
import main.api.response.LogoutResponse;
import main.repository.CaptchaRepository;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class RestorePasswordService {

    private final UsersRepository usersRepository;
    private final CaptchaRepository captchaRepository;
    private Sender sslSender = new Sender("blogobotik@gmail.com", "test1122");

    public RestorePasswordService(UsersRepository usersRepository, CaptchaRepository captchaRepository) {
        this.usersRepository = usersRepository;
        this.captchaRepository = captchaRepository;
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
        Map<String,String> error = new HashMap<>();

        if (usersRepository.findByCode(code).size() == 0) {
            changeProfileResponse.setResult(false);
            error.put("captcha","Ссылка для восстановления пароля устарела.<a href=\"/auth/restore\">Запросить ссылку снова</a>");
            changeProfileResponse.setErrors(error);
        } else if (password.length()<6) {
            changeProfileResponse.setResult(false);
            error.put("password","Пароль короче 6-ти символов");
            changeProfileResponse.setErrors(error);
        } else if (captchaRepository.findByCaptcha(captcha, captchaSecret).size() == 0) {
            changeProfileResponse.setResult(false);
            error.put("captcha","Код с картинки введён неверно");
            changeProfileResponse.setErrors(error);
        } else {
            changeProfileResponse.setResult(true);
            usersRepository.newPassword(password, code);
        }

        return changeProfileResponse;
    }
}
