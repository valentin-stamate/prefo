package com.valentinstamate.prefobackend.models;

import com.valentinstamate.prefobackend.persistence.consts.UserType;

public class UserJwtPayload {
    private final String username;
    private final UserType userType;

    public UserJwtPayload(String username, UserType userType) {
        this.username = username;
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }
}
