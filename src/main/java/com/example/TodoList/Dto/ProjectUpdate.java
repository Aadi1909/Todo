package com.example.TodoList.Dto;


import com.example.TodoList.Model.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUpdate {

    private String description;

    List<Integer> tasks;
}
