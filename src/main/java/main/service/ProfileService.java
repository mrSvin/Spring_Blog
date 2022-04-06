package main.service;

import main.api.response.ChangeProfileResponse;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProfileService {

    private final UsersRepository usersRepository;

    public ProfileService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public ChangeProfileResponse changeProfile(String authCoocie, MultipartFile photo, String email,
                                               String name, String password, Integer removePhoto) throws IOException {
        ChangeProfileResponse changeProfileResponse = new ChangeProfileResponse();

        int idUser = LoginService.sessions.get(authCoocie);

        System.out.println("Size " + photo.getSize());
        System.out.println(email);
        System.out.println(name);
        System.out.println(password);
        System.out.println(removePhoto);

        Map<String, String> error = new HashMap<>();

        if (usersRepository.findByEmail(email).size()>0) {
            changeProfileResponse.setResult(false);
            error.put("email", "Этот e-mail уже зарегистрирован");
            changeProfileResponse.setErrors(error);
        } else if (photo.getSize()>5242880) {
            changeProfileResponse.setResult(false);
            error.put("photo", "Фото слишком большое, нужно не более 5 Мб");
            changeProfileResponse.setErrors(error);
        } else if (name.length()<3) {
            changeProfileResponse.setResult(false);
            error.put("name", "Имя указано неверно");
            changeProfileResponse.setErrors(error);
        } else if (password.length()<6) {
            changeProfileResponse.setResult(false);
            error.put("password", "Пароль короче 6-ти символов");
            changeProfileResponse.setErrors(error);
        } else {
            changeProfileResponse.setResult(true);

            InputStream initialStream = photo.getInputStream();
            byte[] buffer = new byte[initialStream.available()];
            initialStream.read(buffer);

            File targetFile = new File("src/main/resources/static/imagesProfile/test.bmp");

            try (OutputStream outStream = new FileOutputStream(targetFile)) {
                outStream.write(buffer);
            }

            usersRepository.changeProfile(email, name, password, "http://localhost:8081/imagesProfile/test.bmp", idUser);


        }


        return  changeProfileResponse;
    }

}
