package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name="captcha_codes")
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "DATETIME")
    @NotNull
    private String time;

    @Column(columnDefinition = "TINYTEXT")
    @NotNull
    private String code;

    @Column(columnDefinition = "TINYTEXT")
    @NotNull
    private String secret_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecret_code() {
        return secret_code;
    }

    public void setSecret_code(String secret_code) {
        this.secret_code = secret_code;
    }


}