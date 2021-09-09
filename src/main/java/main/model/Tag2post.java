package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Tag2post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Posts post;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Tags tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Posts getPost() {
        return post;
    }

    public void setPost(Posts post) {
        this.post = post;
    }

    public Tags getTag() {
        return tag;
    }

    public void setTag(Tags tag) {
        this.tag = tag;
    }

}