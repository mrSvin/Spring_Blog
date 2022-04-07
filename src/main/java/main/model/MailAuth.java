package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name="mail_auth")
public class MailAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(45)")
    @NotNull
    private String mail;

    @Column(columnDefinition = "VARCHAR(45)")
    @NotNull
    private String password;

}
