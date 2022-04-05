package main.service;

import main.api.response.ChangeProfileResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProfileService {

    public ChangeProfileResponse changeProfile(String authCoocie, MultipartFile photo, String email,
                                               String name, String password, Integer removePhoto) {
        ChangeProfileResponse changeProfileResponse = new ChangeProfileResponse();

        int idUser = LoginService.sessions.get(authCoocie);

//        System.out.println("Name " + photo.getName());
//        System.out.println("Size " + photo.getSize());


        System.out.println(email);
        System.out.println(name);
        System.out.println(password);
        System.out.println(removePhoto);
        System.out.println(photo.getName());

        changeProfileResponse.setResult(false);

        Map<String, String> error = new HashMap<>();
        changeProfileResponse.setErrors(error);

        return  changeProfileResponse;
    }

}
