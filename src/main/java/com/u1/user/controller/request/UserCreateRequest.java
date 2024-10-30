package com.u1.user.controller.request;

import com.u1.user.entity.Gender;
import com.u1.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Data;

@Data
public class UserCreateRequest {

  @NotBlank(message = "名を入力して下さい")
  private String firstName;

  @NotBlank(message = "姓を入力して下さい")
  private String lastName;

  @NotNull(message = "生年月日を入力して下さい")
  @Past(message = "過去の日付を入力して下さい")
  private LocalDate birthday;

  @NotBlank(message = "性別を入力して下さい")
  @Pattern(regexp = "male|female", message = "maleまたはfemaleで入力して下さい")
  private String gender;

  @NotBlank(message = "電話番号を入力して下さい")
  @Pattern(regexp = "^(0[789]0)-\\d{4}-\\d{4}$", message = "070,080,090のいずれかでハイフンを含む11ケタで入力して下さい")
  private String mobilePhone;

  @NotBlank(message = "メールアドレスを入力して下さい")
  @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+$", message = "メールアドレスの形式で入力して下さい")
  private String email;

  @NotBlank(message = "パスワードを入力して下さい")
  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*()_+{}\\[\\]:;\"'<>?,./]).{8,16}$",
      message = "パスワードは大文字、小文字、記号を含む8文字以上16文字以下で入力して下さい")
  private String password;


  public User convertToEntity() {
    return new User(firstName, lastName, birthday, Gender.valueOf(gender.toLowerCase()),
        mobilePhone,
        email, password);


  }

  public UserCreateRequest(String firstName, String lastName, LocalDate birthday, String gender,
      String mobilePhone, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.gender = gender;
    this.mobilePhone = mobilePhone;
    this.email = email;
    this.password = password;
  }
}
