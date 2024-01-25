package com.u1.user.entity;

import java.time.LocalDate;

public class User {
    private Integer id;
    private String name;
    private LocalDate birthday;

    public User(Integer id, String name, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public static User createUser(String name, LocalDate birthday) {
        return new User(null, name, birthday);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
