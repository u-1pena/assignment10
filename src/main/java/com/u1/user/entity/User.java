package com.u1.user.entity;

import java.time.LocalDate;
import lombok.Data;


@Data
public class User {

  private Integer id;
  private String firstName;
  private String lastName;
  private LocalDate birthday;
  private Gender gender;
  private String phoneNumber;
  private String email;
  private String password;

  public User(Integer id, String firstName, String lastName, LocalDate birthday, Gender gender,
      String phoneNumber, String email, String password) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password = password;
  }

  public User(String firstName, String lastName, LocalDate birthday, Gender gender,
      String phoneNumber, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthday = birthday;
    this.gender = gender;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.password = password;
  }


}
