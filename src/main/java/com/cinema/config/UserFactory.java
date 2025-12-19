package com.cinema.config;

import com.cinema.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserFactory {
    private static final Logger log = LoggerFactory.getLogger(UserFactory.class);

    public static User createUser(String email, String username, String fullName, 
                                  String password, User.UserRole role) {
        log.info("Creating user with role: {}", role);
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPassword(password);
        user.setRole(role);
        user.setEnabled(true);
        return user;
    }

    public static User createAdmin(String email, String username, String fullName, String password) {
        return createUser(email, username, fullName, password, User.UserRole.ADMIN);
    }

    public static User createCustomer(String email, String username, String fullName, String password) {
        return createUser(email, username, fullName, password, User.UserRole.CUSTOMER);
    }
}