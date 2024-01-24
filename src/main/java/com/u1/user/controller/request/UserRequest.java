package com.u1.user.controller.request;

import java.util.Date;

public class UserRequest {
    private String name;
    private Date birthday;

    public UserRequest(String name, Date birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }
}
