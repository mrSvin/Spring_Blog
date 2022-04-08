package main.controller;

import com.github.cage.Cage;
import com.github.cage.GCage;
import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.request.RestoreRequest;
import main.api.response.*;
import main.service.CaptchaService;
import main.service.LoginService;
import main.service.RegisterService;
import main.service.RestorePasswordService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiAuthController {

    private final CaptchaService captchaService;
    private final RegisterService registerService;
    private final LoginService loginService;
    private final RestorePasswordService restorePasswordService;

    public ApiAuthController(CaptchaService captchaService, RegisterService registerService, LoginService loginService, RestorePasswordService restorePasswordService) {
        this.captchaService = captchaService;
        this.registerService = registerService;
        this.loginService = loginService;
        this.restorePasswordService = restorePasswordService;
    }

    @GetMapping("/auth/captcha")
    private CaptchaResponse Captcha() throws IOException {
        return captchaService.captchaGenerate();
    }

    @PostMapping("/auth/register")
    private RegisterResponse Register(@RequestBody RegisterRequest registerRequest) {
        return registerService.register(registerRequest.getEmail(), registerRequest.getPassword(),
                registerRequest.getName(), registerRequest.getCaptcha(), registerRequest.getCaptchaSecret());
    }


    @PostMapping("/auth/login")
    public LoginResponse login(HttpServletResponse response,@RequestBody LoginRequest loginRequest) {
            Cage cage = new GCage();
            String authSession = cage.getTokenGenerator().next();
            Cookie cookie = new Cookie("auth", authSession);//создаем объект Cookie,
            cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
            cookie.setPath("/");
            response.addCookie(cookie);//добавляем Cookie в запрос
            response.setContentType("text/plain");//устанавливаем контекст
            return loginService.login(loginRequest.getEmail(), loginRequest.getPassword(), authSession);
    }


    @GetMapping("/auth/logout")
    private LogoutResponse Logout(HttpServletResponse response, @CookieValue(value = "auth", defaultValue = "") String authCoocie) {
        Cookie cookie = new Cookie("auth",  "");
        cookie.setPath("/");
        System.out.println(authCoocie);
        response.addCookie(cookie);
        return loginService.logout(authCoocie);
    }

    @PostMapping("/auth/restore")
    public LogoutResponse Restore(@RequestBody RestoreRequest restoreRequest) {
        return restorePasswordService.restorePassword(restoreRequest.getEmail());
    }

    @PostMapping("/auth/password")
    public ChangeProfileResponse Restore(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return restorePasswordService.changePassword(changePasswordRequest.getCode(), changePasswordRequest.getPassword(),
                changePasswordRequest.getCaptcha(), changePasswordRequest.getCaptchaSecret());
    }

}
