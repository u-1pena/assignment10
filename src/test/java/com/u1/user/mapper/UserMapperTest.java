package com.u1.user.mapper;

import com.u1.user.entity.User;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-users.sql", "classpath:/sqlannotation/insert-users.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )

    @Transactional
    void 全てのユーザーが取得できること() {
        List<User> users = userMapper.findAll();
        assertThat(users)
                .hasSize(3)
                .contains(
                        new User(1, "yuichi", "1984-07-03"),
                        new User(2, "kana", "1993-12-31"),
                        new User(3, "tom", "1995-09-25")
                );
    }

    @Test

    @Transactional
    void 存在するユーザーのIDを指定した時に正常にユーザーが返されること() {
        Optional<User> users = userMapper.findById(1);
        assertThat(users).contains(new User(1, "yuichi", "1984-07-03"));
    }

    @Test

    @Transactional
    void 存在しないユーザーIDを指定した時に例外処理が返されること() {
        Optional<User> users = userMapper.findById(500);
        assertThat(users).isEmpty();
    }
}
