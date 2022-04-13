package main.service;

import main.api.response.PostAddResponse;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AddImageService {

    private final UsersRepository usersRepository;

    public AddImageService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Object addImage(String authCoocie, MultipartFile image) throws IOException {
        PostAddResponse postAddResponse = new PostAddResponse();

        int idUser = LoginService.sessions.get(authCoocie);
        Map<String, String> error = new HashMap<>();

        if (image.getSize() > 5242880) {
            postAddResponse.setResult(false);
            error.put("image", "Размер файла превышает допустимый размер 5 мб");
            postAddResponse.setErrors(error);
            return postAddResponse;
        } else {
            postAddResponse.setResult(true);
            String nameImage = randomNameGeneration();
            writeImageInServer(image, nameImage);

            String request = "/upload/" + nameImage + ".png";
            return request;
        }

    }

    public String randomNameGeneration() {
        String uuid = "ab/" + UUID.randomUUID().toString();
        return uuid;

    }

    public void writeImageInServer(MultipartFile image, String nameImage) throws IOException {
        InputStream initialStream = image.getInputStream();
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        BufferedImage imageBuffer = ImageIO.read(bais);

        ImageIO.write(imageBuffer, "png", new File("src/main/resources/upload/" + nameImage + ".png"));

    }

}
