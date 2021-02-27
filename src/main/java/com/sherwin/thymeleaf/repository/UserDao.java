package com.sherwin.thymeleaf.repository;

import com.sherwin.thymeleaf.model.User;

public interface UserDao {

    Iterable<User> findAll();

    boolean save(User user);

    boolean update(User user);

    User findByUsername(String username);

    boolean deleteById(Long id);
}
