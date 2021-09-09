package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Post_comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "INT")
    private String parent_id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Users user;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Posts post;

    @Column(columnDefinition = "DATETIME")
    @NotNull
    private String time;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
