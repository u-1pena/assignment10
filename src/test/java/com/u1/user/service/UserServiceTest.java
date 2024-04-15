package com.u1.user.service;

import com.u1.user.controller.response.UserNotFoundException;
import com.u1.user.entity.User;
import com.u1.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void 指定したIDで存在するユーザーを取得すること() {
        doReturn(Optional.of(new User(1, "yuichi", "1984-07-03"))).when(userMapper).findById(1);
        User actual = userService.findUser(1);
        assertThat(actual).isEqualTo(new User(1, "yuichi", "1984-07-03"));
        verify(userMapper).findById(1);
    }

    @Test
    void 存在しないIDを指定したとき例外処理を返すこと() throws UserNotFoundException {
        doReturn(Optional.empty()).when(userMapper).findById(0);
        assertThrows(UserNotFoundException.class, () -> {
            userService.findUser(0);
        });

    }

    @Test
    void 指定した名前で存在するユーザーを取得すること() {
        doReturn(Optional.of(new User(1, "yuichi", "1984-07-03"))).when(userMapper).findByName("yuichi");
        User actual = userService.findByName("yuichi");
        assertThat(actual).isEqualTo(new User(1, "yuichi", "1984-07-03"));
        verify(userMapper).findByName("yuichi");

    }

    @Test
    void 存在しない名前を指定したとき例外処理を返すこと() throws UserNotFoundException {
        doReturn(Optional.empty()).when(userMapper).findByName("tarou");
        assertThrows(UserNotFoundException.class, () -> {
            userService.findByName("tarou");
        });

    }

}
