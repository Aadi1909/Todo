package com.example.TodoList.Dto;


import com.example.TodoList.Model.Organisation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrgLogin {
    private String email;
    private String password;
}

