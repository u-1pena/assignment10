package com.u1.user.mapper;

import com.u1.user.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    /*ユーザーを文字検索する処理*/
    @Select("SELECT * FROM users WHERE name LIKE CONCAT(#{prefix}, '%') AND name LIKE CONCAT('%', #{suffix}) AND name LIKE CONCAT('%', #{contains}, '%')")
    List<User> findByUserStartingWith(String prefix, String suffix, String contains);

    /*ユーザーを{id}で検索する処理*/
    @Select("SELECT * FROM users WHERE id = #{id}")
    Optional<User> findById(int id);

    /*ユーザー登録する処理*/
    @Insert("INSERT INTO users (name, birthday) VALUES (#{name}, #{birthday})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(Integer id);
}
