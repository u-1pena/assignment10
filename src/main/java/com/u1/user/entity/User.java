package com.u1.user.entity;

import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private Date birthday;

    public User(Integer id, String name, Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public static User createUser(String name, Date birthday) {
        return new User(null, name, birthday);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }
}
