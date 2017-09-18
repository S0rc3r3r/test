package com.muso.models;

import lombok.Getter;

@Getter
public class User {

    private String username;
    private String password;
    private String jwt_token;
    private int userId;

}
