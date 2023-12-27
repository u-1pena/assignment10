package com.u1.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserMapper userMapper;

    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/users")
    public List<User> findByNames(UserSearchRequest request) {
        return userMapper.findByUserStartingWith(request.getStartsWith(), request.getEndsWith(), request.getContains());
    }
}
