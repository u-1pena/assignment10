package com.u1.user.service;

import com.u1.user.controller.response.UserNotFoundException;
import com.u1.user.entity.User;
import com.u1.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //ユーザーを全件取得する仕様
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /*-------------------ユーザーを検索する仕様--------------------------*/

    //[id検索]
    public User findUser(int id) {
        return this.userMapper.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found with id: " + id));
    }

    //[名前検索]

    public User findByName(String name) {
        return this.userMapper.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("user not found containing name: " + name));
    }

    /*-------------------ユーザーを登録する仕様--------------------------*/
    public User insert(String name, String birthday) {//staticメソッドを利用
        User newUser = User.createUser(name, birthday);
        userMapper.insert(newUser);
        return newUser;
    }

    //    ユーザーを削除する仕様
    public User delete(int id) {
        User user = this.userMapper.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found with id: " + id));
        userMapper.delete(id);
        return user;
    }

    //    ユーザーを更新する仕様
    public User update(int id, String name, String birthday) {
        User user = this.userMapper.findById(id)
                .orElseThrow(() -> new UserNotFoundException("user not found with id: " + id));

        user.setName(name);
        user.setBirthday(birthday);

        userMapper.update(user);
        return user;
    }
}

