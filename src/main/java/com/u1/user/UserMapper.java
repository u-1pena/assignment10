package com.u1.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE name LIKE CONCAT(#{prefix}, '%') AND name LIKE CONCAT('%', #{suffix}) AND name LIKE CONCAT('%', #{contains}, '%')")
    List<User> findByUserStartingWith(String prefix, String suffix, String contains);
}
