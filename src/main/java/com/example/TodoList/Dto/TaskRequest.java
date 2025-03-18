package com.example.TodoList.Dto;

import com.example.TodoList.Model.Task;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class TaskRequest {

  
  private Integer taskId;

  @NotNull
  private String taskname;

  @NotNull
  private String taskdesc;

  // @Builder.Default
  // private Boolean isCompleted = null;

  public Task to() {
    return Task.builder()
        .id(this.taskId)
        .taskName(this.taskname)
        .taskDescription(this.taskdesc)
        .build();
  }
}
