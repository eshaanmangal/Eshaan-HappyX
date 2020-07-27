package com.xebia.happix.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {

    private String username;
    private String password;
    private int age;
    private String email;
}
