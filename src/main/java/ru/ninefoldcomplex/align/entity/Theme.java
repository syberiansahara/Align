package ru.ninefoldcomplex.align.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Theme {

    @Id
    private long id;

    private String theme;

    public Theme(long id, String theme) {
        this.id = id;
        this.theme = theme;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}