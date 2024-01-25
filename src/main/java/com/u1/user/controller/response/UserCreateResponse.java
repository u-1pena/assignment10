package com.u1.user.controller.response;

/*UserControllerのPOST処理時"user created"を返すクラス*/
public class UserCreateResponse {

    private String message;

    public UserCreateResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
