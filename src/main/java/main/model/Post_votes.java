package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Post_votes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Users user;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Posts post;

    @Column(columnDefinition = "DATETIME")
    @NotNull
    private String time;

    @Column(columnDefinition = "TINYINT")
    @NotNull
    private String value;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

}
