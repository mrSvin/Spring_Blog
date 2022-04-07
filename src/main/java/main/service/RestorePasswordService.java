package main.service;

import main.repository.MailAuthRepository;
import org.springframework.stereotype.Service;

@Service
public class RestorePasswordService {

    private final MailAuthRepository mailAuthRepository;

    private Sender sslSender = new Sender("***", "***");

    public RestorePasswordService(MailAuthRepository mailAuthRepository) {
        this.mailAuthRepository = mailAuthRepository;
    }

    public void restorePassword() {
        sslSender.send("Test Theme", "Тестовая отправка сообщения!", "albu@sespel.com");
    }
}
