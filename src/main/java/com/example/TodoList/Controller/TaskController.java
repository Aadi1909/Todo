package com.example.TodoList.Controller;

import com.example.TodoList.Dto.TaskRequest;
import com.example.TodoList.Model.Task;
import com.example.TodoList.Model.User;
import com.example.TodoList.Service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    private int getAuthenticatedUserOrganisationId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Assuming your User class is used in authentication
        return Math.toIntExact(user.getOrganisation().getId());
    }


   @GetMapping("/getAllTasks/{projectId}")
   public ResponseEntity<?> getAllTasks(@PathVariable int projectId) {
        int orgId = getAuthenticatedUserOrganisationId();
        List<Task> tasks = taskService.findAll(orgId, projectId);
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

