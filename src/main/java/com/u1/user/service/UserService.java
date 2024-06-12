package com.u1.user.service;

import com.u1.user.controller.response.UserNotFoundException;
import com.u1.user.entity.User;
import com.u1.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    // dependency injection constructor injection field injection final:授業メモ
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

    /*public 外から使うControllerから使うから　User 戻り値の型　何故Userか・・←レスポンスする時にIDが必要だから
    insert(これは自由に命名）（）内は引数input 登録には２つ必要。name,birthdayともにString型
    */
    public User insert(String name, String birthday) {//メソッドのSignature
        User user = new User(name, birthday);
        userMapper.insert(user);
        return user;
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
