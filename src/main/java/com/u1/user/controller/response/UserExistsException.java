package com.u1.user.controller.response;

public class UserExistsException extends RuntimeException {

  public UserExistsException(String message) {
    super(message);

  }

}
