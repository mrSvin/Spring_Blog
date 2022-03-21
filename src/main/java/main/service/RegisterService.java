package main.service;

import main.api.response.RegisterResponse;
import main.model.User;
import main.repository.CaptchaRepository;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RegisterService {
    private final CaptchaRepository captchaRepository;
    private final UsersRepository usersRepository;

    public RegisterService(CaptchaRepository captchaRepository, UsersRepository usersRepository) {
        this.captchaRepository = captchaRepository;
        this.usersRepository = usersRepository;
    }

    public RegisterResponse register(String email, String password, String name, String captcha, String captchaSecret) {
        RegisterResponse registerResponse = new RegisterResponse();

        Map<String, String> checkError = checkAuth(email, password, name, captcha, captchaSecret);

        if (checkError == null) {
            registerResponse.setResult(true);
            saveUser(email, password, name, captchaSecret);
        } else {
            registerResponse.setResult(false);
            registerResponse.setErrors(checkError);
        }
        return registerResponse;
    }

    private Map<String, String> checkAuth(String email, String password, String name, String captcha, String captchaSecret) {

        int emailRepo = usersRepository.findByEmail(email).size();
        int captchaRepo = captchaRepository.findByCaptcha(captcha, captchaSecret).size();
        System.out.println("size " + captchaRepo);
        String trueName = name.replaceAll(" ", "");

        Map<String, String> error = new HashMap<>();

        if (emailRepo > 0) {
            error.put("email", "Этот e-mail уже зарегистрирован");
        } else if (name.length()<3 || name.length()>20 || trueName.length() != name.length()) {
            error.put("name", "Имя указано неверно");
        } else if(password.length()<6) {
            error.put("password", "Пароль короче 6-ти символов");
        } else if(captchaRepo == 0) {
            error.put("captcha", "Код с картинки введён неверно");
        }
        else {
            error = null;
        }
        return error;
    }

    private void saveUser(String email, String password, String name, String captchaSecret) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setCode(captchaSecret);
        user.setIs_moderator(0);
        user.setReg_time(timeNow());
        usersRepository.save(user);
    }

    private Date timeNow() {
        long unixTime = System.currentTimeMillis() / 1000L; //Определяем текущее время
        Date date = new java.util.Date(unixTime * 1000L);
        return date;
    }

}
