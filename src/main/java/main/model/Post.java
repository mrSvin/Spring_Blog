package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TINYINT")
    @NotNull
    private String is_active;

    @Column(columnDefinition = "ENUM(\"NEW\", \"ACCEPTED\", \"DECLINED\")")
    @NotNull
    private String moderation_status;

    @Column(columnDefinition = "INT")
    private String moderator_id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private User user;

    @Column(columnDefinition = "DATETIME")
    @NotNull
    private Date time;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotNull
    private String text;

    @Column(columnDefinition = "INT")
    @NotNull
    private String view_count;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getModeration_status() {
        return moderation_status;
    }

    public void setModeration_status(String moderation_status) {
        this.moderation_status = moderation_status;
    }

    public String getModerator_id() {
        return moderator_id;
    }

    public void setModerator_id(String moderator_id) {
        this.moderator_id = moderator_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}