package com.u1.user.mapper;

import com.u1.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

  /*ユーザーを全件取得する処理*/
  @Select("SELECT * FROM users")
  List<User> findAll();

  /*ユーザーを名前で検索する機能*/
  @Select("SELECT * FROM users WHERE name LIKE CONCAT('%', #{name}, '%')")
  Optional<User> findByName(String name);


  /*****************************************************
   使っていないので保留！あとで復習するように保存！

   ユーザーを文字検索する処理@Select("SELECT * FROM users WHERE name LIKE CONCAT(#{prefix}, '%') AND name LIKE CONCAT('%', #{suffix}) AND name LIKE CONCAT('%', #{contains}, '%')")

   List<User> findByName(String prefix, String suffix, String contains);
   *****************************************************/

  /*ユーザーを{id}で検索する処理*/
  @Select("SELECT * FROM users WHERE id = #{id}")
  Optional<User> findById(int id);

  /*ユーザー登録する処理*/
  @Insert("INSERT INTO users (first_name, last_name, birthday, gender, mobile_phone, email, password) VALUES (#{firstName}, #{lastName}, #{birthday}, #{gender}, #{phoneNumber}, #{email}, #{password})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(User user);

  /*ユーザー削除する処理*/
  @Delete("DELETE FROM users WHERE id = #{id}")
  void delete(int id);

  /*ユーザーを更新する処理*/
  @Update("UPDATE users SET name = #{name}, birthday = #{birthday} WHERE id = #{id}")
  void update(User user);

}

