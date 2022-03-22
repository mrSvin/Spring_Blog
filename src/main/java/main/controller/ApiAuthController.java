package main.controller;

import main.api.request.RegisterRequest;
import main.api.response.CaptchaResponse;
import main.api.response.RegisterResponse;
import main.service.CaptchaService;
import main.service.RegisterService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiAuthController {

    private final CaptchaService captchaService;
    private final RegisterService registerService;

    public ApiAuthController(CaptchaService captchaService, RegisterService registerService) {
        this.captchaService = captchaService;
        this.registerService = registerService;
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

}
