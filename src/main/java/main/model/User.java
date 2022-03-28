package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name="users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "TINYINT")
    @NotNull
    private int is_moderator;
    @Column(columnDefinition = "DATETIME")
    @NotNull
    private Date reg_time;
    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String name;
    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String email;
    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String password;
    @Column(columnDefinition = "VARCHAR(255)")
    private String code;
    @Column(columnDefinition = "TEXT")
    private String photo;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return null;
    }

    public int getIs_moderator() {
        return is_moderator;
    }

    public void setIs_moderator(int is_moderator) {
        this.is_moderator = is_moderator;
    }

    public Date getReg_time() {
        return reg_time;
    }

    public void setReg_time(Date reg_time) {
        this.reg_time = reg_time;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


}
