package com.muso.dataproviders;

import com.muso.models.User;

public class UserDataProvider extends AbstractDataProvider<User> {

    public UserDataProvider(String jsonFilePath) {
        super(jsonFilePath, User.class);

    }

}
