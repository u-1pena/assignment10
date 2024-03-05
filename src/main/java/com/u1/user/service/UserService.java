package com.u1.user.service;

import com.u1.user.controller.response.UserNotFoundException;
import com.u1.user.entity.User;
import com.u1.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //ユーザーを検索する仕様
    public User findUser(int id) {
        return this.userMapper.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found with id: " + id));
    }

    /*ユーザーを登録する仕様*/
    public User insert(String name, String birthday) {//staticメソッドを利用
        User newUser = User.createUser(name, birthday);
        userMapper.insert(newUser);
        return newUser;
    }

    public User delete(int id) {
        User user = this.userMapper.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found with id: " + id));
        userMapper.delete(id);
        return user;
    }
}

