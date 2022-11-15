package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.models.User;

public class UserMocks {

    public static User getSingleUser(){
        final User user = new User();
        user.setEmail("contact@domain.com");
        user.setName("Admin 222");
        user.setId(64662L);
        return user;
    }

}
