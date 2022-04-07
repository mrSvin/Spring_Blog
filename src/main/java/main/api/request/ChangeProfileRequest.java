package main.api.request;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class ChangeProfileRequest {

    private String name;
    private String email;
    private String password;
    private Integer removePhoto = 0;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRemovePhoto() {
        return removePhoto;
    }

    public void setRemovePhoto(Integer removePhoto) {
        this.removePhoto = removePhoto;
    }

}
