package com.u1.user.service;

import com.u1.user.entity.User;
import com.u1.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UserMapper userMapper;

    @Test
    void 全てのユーザーが取得できること() {
        List<User> users = List.of(
                new User(1, "yuichi", "1984-07-03"),
                new User(2, "kana", "1993-12-31"),
                new User(3, "tom", "1995-09-25")
        );

        doReturn(users).when(userMapper).findAll();
        List<User> actual = userService.findAll();
        assertThat(actual).isEqualTo(users);
        verify(userMapper).findAll();
    }

}
