package com.xebia.happix.service;


import com.xebia.happix.model.User;
import com.xebia.happix.model.UserDto;

import java.util.List;

public interface UserService {

    User save(UserDto user);
    List<User> findAll();
    void delete(long id);
    User findOne(String username);

    User findById(Long id);
}
