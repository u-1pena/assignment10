package com.u1.user.controller.response;

/*UserControllerのPOST処理時"user created"を返すクラス*/
public class UserResponse {

    private String message;

    public UserResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
