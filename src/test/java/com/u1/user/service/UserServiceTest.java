package com.u1.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.u1.user.controller.response.UserNotFoundException;
import com.u1.user.entity.Gender;
import com.u1.user.entity.User;
import com.u1.user.mapper.UserMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  UserService userService;

  @Mock
  UserMapper userMapper;

  @Nested
  class ReadClass {

    @Test
    void 全てのユーザーが取得できること() {
      List<User> users = List.of(
          new User(1, "yuichi", "shima", LocalDate.parse("1984-07-03"), Gender.male,
              "090-1234-5678", "shimaichi5973@gmail.com", "Yuichi@0703"),
          new User(2, "kana", "nishiyama", LocalDate.parse("1993-12-31"), Gender.female,
              "080-1234-5678", "kana1234@yahoo.com", "MarinTokyo@2023"));

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
      doReturn(Optional.of(new User(1, "yuichi", "1984-07-03"))).when(userMapper)
          .findByName("yuichi");
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

  @Nested
  class CreateClass {

    @Test
    void 新規登録すること() {
      User user = new User(null, "testFirstName", "testLastName", LocalDate.parse("2000-01-01"),
          Gender.male,
          "090-1111-2222", "test@aol.com", "RaiseThech@0101");
      doNothing().when(userMapper).insert(user);
      userService.insert(user);
      verify(userMapper).insert(user);
    }
  }

  @Nested
  class DeleteClass {

    @Test
    void 指定したIDに紐づいて登録されたユーザーを削除すること() {
      doReturn(Optional.of(new User(1, "yuichi", "1984-07-03"))).when(userMapper).findById(1);
      User actual = userService.delete(1);
      assertThat(actual).isEqualTo(new User(1, "yuichi", "1984-07-03"));
      verify(userMapper).findById(1);
      verify(userMapper).delete(1);
    }

    @Test
    void 存在しないIDで削除しようとしたとき例外処理を返しDBが削除されないこと() throws UserNotFoundException {
      doReturn(Optional.empty()).when(userMapper).findById(0);
      assertThrows(UserNotFoundException.class, () -> {
        userService.delete(0);
        verify(userMapper).findById(0);
        verify(userMapper, never()).delete(0);
      });

    }


  }

  @Nested
  class UpdateClass {

    @Test
    void 指定したIDに紐づいて登録されたユーザーの内容を変更すること() {
      doReturn(Optional.of(new User(1, "updateUser", "2000-01-01"))).when(userMapper).findById(1);
      userService.update(1, "updateUser", "2000-01-01");
      User user = new User(1, "updateUser", "2000-01-01");
      verify(userMapper).findById(1);
      verify(userMapper).update(user);
    }

    @Test
    void 存在しないIDで更新しようとしたとき例外処理を返しDBの更新が行われないこと() throws UserNotFoundException {
      doReturn(Optional.empty()).when(userMapper).findById(0);
      assertThrows(UserNotFoundException.class, () -> {
        userService.update(0, "yuichi", "1984-07-03");
        verify(userMapper).findById(0);
        verify(userMapper, never()).update(any());
      });

    }
  }

}
