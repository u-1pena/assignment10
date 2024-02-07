package com.u1.user.entity;

public class User {
    private Integer id;
    private String name;
    private String birthday;

    public User(Integer id, String name, String birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public static User createUser(String name, String birthday) {
        return new User(null, name, birthday);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }
}
