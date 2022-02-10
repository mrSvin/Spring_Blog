package main.model;

import javax.persistence.*;

@Entity
public class Tag2post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Post post;

    @ManyToOne (optional=false, cascade=CascadeType.ALL)
    private Tag tag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

}