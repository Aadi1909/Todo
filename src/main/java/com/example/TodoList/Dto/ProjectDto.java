package com.example.TodoList.Dto;


import com.example.TodoList.Model.Projects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    // create a project using project dto with given org id and assign the users
    private String name;

    private String description;

    public Projects to() {
        return Projects.builder()
                .description(this.description)
                .name(this.name)
                .build();
    }

    // then user project controller to add or remove users that will work on the specific task

}
