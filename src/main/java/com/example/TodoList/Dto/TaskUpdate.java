package com.example.TodoList.Dto;


import com.example.TodoList.Model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdate {

    private String taskDescription;

    private List<User> usersRemove;

    private List<User> usersAdd;

    private Boolean isCompleted;
}
