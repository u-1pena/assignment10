package com.u1.user.controller;

import com.u1.user.entity.User;
import com.u1.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")//ユーザー検索する処理
    public User getUser(@PathVariable("id") int id) {
        return userService.findUser(id);
    }


}
