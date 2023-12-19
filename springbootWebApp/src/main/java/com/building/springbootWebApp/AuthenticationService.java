package com.building.springbootWebApp;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    public boolean authenticate(String username, String password ){
        boolean isValidUserName = username.equalsIgnoreCase("user");
        boolean isValidPassword= password.equalsIgnoreCase("password");

        return (isValidPassword && isValidUserName);
    }
}
