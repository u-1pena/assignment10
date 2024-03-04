package com.u1.user.controller;

import com.u1.user.controller.request.UserRequest;
import com.u1.user.controller.response.UserResponse;
import com.u1.user.entity.User;
import com.u1.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<UserResponse> insert(@RequestBody @Validated UserRequest userRequest, UriComponentsBuilder uriBuilder) {
        User user = userService.insert(userRequest.getName(), userRequest.getBirthday());
        URI location = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        UserResponse body = new UserResponse("user created");
        return ResponseEntity.created(location).body(body);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserResponse> delete(@PathVariable("id") int id) {
        User user = userService.delete(id);
        UserResponse body = new UserResponse("a deleted user!");
        return ResponseEntity.ok(body);
    }


}
