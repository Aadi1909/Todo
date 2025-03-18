package com.example.TodoList.Controller;


import com.example.TodoList.Dto.*;
import com.example.TodoList.Model.Projects;
import com.example.TodoList.Model.Task;
import com.example.TodoList.Model.User;
import com.example.TodoList.Service.OrganisationService;
import com.example.TodoList.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/organisation")
public class OrganisationController {

    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private ProjectService projectService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody OrgRegister orgRegister) {
        return organisationService.register(orgRegister);
    }

    //GET ALL THE PROJECTS FOR THIS ORGANISATIONS

    @GetMapping("/get-all-projects/{organisationId}")
    public ResponseEntity<List<Projects>> getAll(@PathVariable int organisationId) {
        List<Projects> allProjects = organisationService.getAll(organisationId);
        if (allProjects == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allProjects);
    }


    //GET ALL THE TASKS UNDER THE PROJECT
        // THAT INCLUDE COMPLETED AND PENDING

    @GetMapping("/get-all-tasks-in-org/{organisationId}/{projectId}")
    public ResponseEntity<List<Task>> getAllTasksUnderOrganisation(@PathVariable int organisationId, @PathVariable int projectId) {
        List<Task> allTasks = organisationService.getAllTask(organisationId, projectId);
        if (allTasks == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allTasks);

        // ADD TO GET COMPLETED AND PENDING PROJECTS AND ALSO TASKS
    }


    //GET ALL THE USERS THAT ARE WORKING ON A SPECIFIC PROJECT

    @GetMapping("/get-all-users/{organisationId}/{projectId}/{taskId}")
    public ResponseEntity<List<User>> getAllUsers(@PathVariable int organisationId, @PathVariable int projectId, @PathVariable int taskId) {
        List<User> allUsers = organisationService.getAllUsersOnTask(organisationId, projectId, taskId);
        if (allUsers == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(allUsers);
    }

    //ASSIGN NEW PROJECT TO AN ORGANISATION

    @PostMapping("/create-project/organisationId")
    public ResponseEntity<String> createProject(@RequestBody Long organisationId, @RequestBody ProjectDto projectDto) {
        return organisationService.createProject(organisationId, projectDto.to());
    }


    //UPDATE THE DETAILS OF ANY PROJECT
    @PutMapping("/update-task/{orgId}/{projectId}/{taskId}")
    public ResponseEntity<String> updateTaskOnProject(@PathVariable int orgId, @PathVariable int projectId, int taskId, @RequestBody TaskUpdate taskUpdate) {
        return organisationService.updateTaskOnProject(orgId, projectId, taskId, taskUpdate);
    }


    // UPDATE THE DETAILS OF ANY TASKS UNDER SOME PROJECT

    @PutMapping("/update-project/{orgId}/{projectId}")
    public ResponseEntity<String> updateProject(@RequestBody ProjectUpdate projectUpdate, @PathVariable int orgId, @PathVariable int projectId) {
        return organisationService.updateProjectInService(projectUpdate, orgId, projectId);

    }

    //DELETE SOME TASKS

    // Delete some projects
    @DeleteMapping("/delete-project/{orgId}")
    public ResponseEntity<String> deleteProjects(@RequestBody DeleteProject deleteProject, int orgId) {
        return organisationService.deleteProjects(deleteProject, orgId);
    }

}
