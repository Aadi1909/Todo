package com.example.TodoList.Controller;


import com.example.TodoList.Dto.TaskUpdate;
import com.example.TodoList.Model.Task;
import com.example.TodoList.Model.User;
import com.example.TodoList.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    // See all the tasks under that project
    @GetMapping("/get-all-tasks/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable int projectId) {
        int orgId = getAuthenticatedUserOrganisationId();
        return projectService.getTasksByProject(projectId, orgId);
    }

    private int getAuthenticatedUserOrganisationId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Assuming your User class is used in authentication
        return Math.toIntExact(user.getOrganisation().getId());
    }


    // update a task in that project
    @PutMapping("/update-task/{projectId}/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int projectId, @PathVariable int taskId, @RequestBody TaskUpdate taskUpdate) {
        int orgId = getAuthenticatedUserOrganisationId();
        return projectService.updateTask(orgId, projectId, taskId, taskUpdate);
    }

    // delete a task in that project

    @DeleteMapping("/detelet-task/{projectId}/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable int projectId, @PathVariable int taskId) {
        int orgId = getAuthenticatedUserOrganisationId();
        return projectService.deleteTask(orgId, projectId, taskId);
    }

    // create a new task in that project and add the users in that project or remove the users from that project

}
