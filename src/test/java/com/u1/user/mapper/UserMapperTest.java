package com.u1.user.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.u1.user.entity.Gender;
import com.u1.user.entity.User;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

  @Autowired
  UserMapper userMapper;
/*----------------------------Spring Test(@sql)verノート用-----------------------------------------------
    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-users.sql", "classpath:/sqlannotation/insert-users.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
   )
    --------------------READ処理(GET)----------------------------------------------------------------*/

  @Nested
  class ReadClass {

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 全てのユーザーが取得できること() {
      List<User> users = userMapper.findAll();
      assertThat(users)
          .hasSize(2)
          .contains(
              new User(1, "yuichi", "1984-07-03"),
              new User(2, "kana", "1993-12-31")
          );
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 指定したIDで存在するユーザーを取得すること() {
      Optional<User> user = userMapper.findById(1);
      assertThat(user).contains(new User(1, "yuichi", "1984-07-03"));
    }

    @Test
    @DataSet(value = "datasets/users.yml")
    @Transactional
    void 指定した名前で存在するユーザーを取得すること() {
      Optional<User> user = userMapper.findByName("y");
      assertThat(user).contains(new User(1, "yuichi", "1984-07-03"));
    }

  }


  @Nested
  class CreateClass {

    @Test
    @DataSet(value = "datasets/users.yml")
    @ExpectedDataSet(value = "datasets/insertUsers.yml", ignoreCols = "id")
    @Transactional
    void ユーザー登録すること() {
      User user = new User("newUserFirstName", "newUserLastName", LocalDate.parse("2000-01-01"),
          Gender.female, "090-1234-5555", "newUser@test.co.jp", "NewUser@0101");
      userMapper.insert(user);
    }
  }

  @Nested
  class DeleteClas {

    @Test
    @DataSet(value = "datasets/users.yml")
    @ExpectedDataSet(value = "datasets/deleteUsers.yml")
    @Transactional
    void 指定したIDに紐づいた存在するユーザーを削除すること() {
      userMapper.delete(1);
    }

  }

  @Nested
  class UpdateClass {

    @Test
    @DataSet(value = "datasets/users.yml")
    @ExpectedDataSet(value = "datasets/updateUsers.yml")
    @Transactional
    void 指定したIDに紐づいたユーザーを変更すること() {
      User user = new User(1, "updateUser", "2000-01-01");
      userMapper.update(user);

    }

  }
}
