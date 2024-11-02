package com.u1.user.controller;

import com.u1.user.controller.request.UserCreateRequest;
import com.u1.user.controller.request.UserUpdateRequest;
import com.u1.user.controller.response.UserResponse;
import com.u1.user.entity.User;
import com.u1.user.service.UserService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/users")
  public List<User> findAll() {
    return userService.findAll();
  }

  @GetMapping("/users/names")
  public User findByName(@RequestParam String name) {
    return userService.findByName(name);
  }

  @GetMapping("/users/{id}")//ユーザー検索する処理
  public User getUser(@PathVariable("id") int id) {
    return userService.findUser(id);
  }

  @PostMapping("/users")//ユーザー登録処理
  public ResponseEntity<UserResponse> insert(
      @RequestBody @Validated UserCreateRequest userCreateRequest,
      UriComponentsBuilder uriBuilder) {
    User user = userService.insert(userCreateRequest.convertToEntity());
    URI location = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
    UserResponse body = new UserResponse("user created");
    return ResponseEntity.created(location).body(body);
  }

  @DeleteMapping("/users/{id}")//ユーザーを削除する処理
  public ResponseEntity<UserResponse> delete(@PathVariable("id") int id) {
    User user = userService.delete(id);
    UserResponse body = new UserResponse("a deleted user!");
    return ResponseEntity.ok(body);
  }

  @PatchMapping("/users/{id}")//ユーザーを更新する処理
  public ResponseEntity<UserResponse> update(@PathVariable("id") int id,
      @RequestBody @Validated UserUpdateRequest userUpdateRequest) {
    User user = userService.update(id, userUpdateRequest.getName(),
        userUpdateRequest.getBirthday());
    UserResponse body = new UserResponse("user updated!");
    return ResponseEntity.ok(body);

  }


}
