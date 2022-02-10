package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name="post_votes")
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private User user;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Post post;

    @Column(columnDefinition = "DATETIME")
    @NotNull
    private String time;

    @Column(columnDefinition = "TINYINT")
    @NotNull
    private Integer value;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
