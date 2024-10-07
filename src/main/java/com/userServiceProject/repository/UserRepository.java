package com.userServiceProject.repository;

import com.userServiceProject.model.User;
import jdk.security.jarsigner.JarSignerException;

import java.util.List;

public interface UserRepository {
    Long createUser(User user);
    void updateUser(User user);
    void deleteUserById(Long id);
    User getUserById(Long id) throws JarSignerException;
    User registerUser(Long userId);
    List<User> getAllUsers();
}
