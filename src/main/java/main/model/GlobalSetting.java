package main.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name="global_settings")
public class GlobalSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String code;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String name;

    @Column(columnDefinition = "VARCHAR(255)")
    @NotNull
    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}