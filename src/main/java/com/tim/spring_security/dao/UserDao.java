package com.tim.spring_security.dao;

import com.tim.spring_security.model.Role;
import com.tim.spring_security.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao {
    void add(User user);

    void delete(long id);

    User change(User user, Set<Role> roles);

    List<User> listUsers();

    User findUserById(long id);

    public User findUserByEmail(String email);
}