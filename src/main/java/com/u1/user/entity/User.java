package com.u1.user.entity;

import java.util.Objects;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override //別のインスタンスだったとしても同じものと扱うメソットequal
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(birthday, user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday);
    }


}
