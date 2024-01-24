package com.u1.user.controller;

import com.u1.user.controller.request.UserRequest;
import com.u1.user.controller.response.UserCreateResponse;
import com.u1.user.entity.User;
import com.u1.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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

    @PostMapping("/users")//ユーザー登録処理
    public ResponseEntity<UserCreateResponse> insert(@RequestBody UserRequest userRequest, UriComponentsBuilder uriBuilder) {
        User user = userService.insert(userRequest.getName(), userRequest.getBirthday());
        URI location = uriBuilder.path("/Users/{id}").buildAndExpand(user.getId()).toUri();
        UserCreateResponse body = new UserCreateResponse("user created");
        return ResponseEntity.created(location).body(body);
    }


}
