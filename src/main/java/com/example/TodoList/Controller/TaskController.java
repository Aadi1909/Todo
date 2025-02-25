package com.example.TodoList.Controller;

import com.example.TodoList.Dto.TaskRequest;
import com.example.TodoList.Model.Task;
import com.example.TodoList.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

   @GetMapping("/getAllTasks")
   public ResponseEntity<?> getAllTasks(){
        List<Task> tasks = taskService.findAll();

       return (ResponseEntity<?>) ResponseEntity.badRequest();
   }

    @PostMapping("/create-task")
    public ResponseEntity<String> createTask(@RequestBody @Valid TaskRequest taskRequest){
        String taskResponse = taskService.createTask(taskRequest.to());
        return ResponseEntity.ok(taskResponse);
    }

    @PutMapping("/update-task/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Integer taskId, @RequestBody @Valid TaskRequest taskRequest){
        String taskResponse = taskService.updateTask(taskId, taskRequest);
        return ResponseEntity.ok(taskResponse);
    }

    @DeleteMapping("/delete-task/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer taskId){
        return ResponseEntity.ok(taskService.deleteTask(taskId));
    }

}

