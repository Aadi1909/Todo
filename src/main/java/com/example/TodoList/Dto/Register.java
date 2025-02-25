package com.example.TodoList.Dto;

import com.example.TodoList.Model.Role;
import com.example.TodoList.Model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Register {
    private String username;
    private String email;
    private String password;

    public User toUser() {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .role(Role.ROLE_USER)
                .build();
    }
}
