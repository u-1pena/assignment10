package com.u1.user.controller.request;

import java.time.LocalDate;

public class UserRequest {
    private String name;
    private LocalDate birthday;

    public UserRequest(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }
}
