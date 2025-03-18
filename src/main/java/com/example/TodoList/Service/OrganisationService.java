package com.example.TodoList.Service;


import com.example.TodoList.Dao.OrganisationRepo;
import com.example.TodoList.Dao.ProjectRepo;
import com.example.TodoList.Dao.TaskRepo;
import com.example.TodoList.Dto.DeleteProject;
import com.example.TodoList.Dto.OrgRegister;
import com.example.TodoList.Dto.ProjectUpdate;
import com.example.TodoList.Dto.TaskUpdate;
import com.example.TodoList.Model.Organisation;
import com.example.TodoList.Model.Projects;
import com.example.TodoList.Model.Task;
import com.example.TodoList.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrganisationService {

    @Autowired
    OrganisationRepo organisationRepo;

    @Autowired
    ProjectRepo projectRepo;

    @Autowired
    TaskRepo taskRepo;

    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<String> register(OrgRegister orgRegister) {
        Organisation organisation = orgRegister.to();
        organisation.setPassword(passwordEncoder.encode(orgRegister.getPassword()));
        Organisation savedOrg = organisationRepo.save(organisation);
        Long id = savedOrg.getId();
        return ResponseEntity.ok("Organisation Registered Successfully " + id);
    }

    public List<Projects> getAll(Integer organisationId) {
        return projectRepo.findAllById(organisationId);
    }

    public List<Task> getAllTask(Integer organisationId, Integer projectId) {
        Organisation organisation = organisationRepo.findById(Long.valueOf(organisationId))
                .orElseThrow(() -> new RuntimeException("Organisation not found"));

        List<Task> tasks = new ArrayList<>();

        for (Projects project : organisation.getProjects()) {
            if (projectId == null || project.getId().equals(projectId)) {
                tasks.addAll(project.getTasks());
            }
        }
        return tasks;
    }

    public List<User> getAllUsersOnTask(Integer organisationId, Integer projectId, Integer taskId) {
        Organisation organisation = organisationRepo.findById(Long.valueOf(organisationId))
                .orElseThrow(() -> new RuntimeException("Organisation not found"));

        return organisation.getProjects().stream()
                .filter(project -> projectId == null || project.getId().equals(projectId))
                .flatMap(project -> project.getTasks().stream())
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .map(Task::getAssignedUsers)
                .orElseThrow(() -> new RuntimeException("Task not found in the specified project and organisation"));
    }

    public ResponseEntity<String> createProject(Long organisationId, Projects projects) {
        Optional<Organisation> org = organisationRepo.findById(organisationId);
        if (org.isEmpty()) {
            return ResponseEntity.badRequest().body("Organisation not found");
        }
        projects.setOrganisation(org.get());
        projectRepo.save(projects);
        return ResponseEntity.ok("Project Created Successfully");
    }

    public ResponseEntity<String> updateTaskOnProject(int organisationId, int projectId, int taskId, TaskUpdate taskUpdate) {
        // Fetch the task based on organisation, project, and task IDs
        Task task = taskRepo.findByProjectOrganisationIdAndProjectIdAndId(organisationId, projectId, taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Update task details
        if (taskUpdate.getTaskDescription() != null && !taskUpdate.getTaskDescription().isEmpty()) {
            task.setTaskDescription(taskUpdate.getTaskDescription());
        }

        if (taskUpdate.getUsersRemove() != null && !taskUpdate.getUsersRemove().isEmpty()) {
            List<User> currentUsers = task.getAssignedUsers();
            currentUsers.removeAll(taskUpdate.getUsersRemove());
            task.setAssignedUsers(currentUsers);
        }

        // Add users to task
        if (taskUpdate.getUsersAdd() != null && !taskUpdate.getUsersAdd().isEmpty()) {
            List<User> currentUsers = task.getAssignedUsers();
            currentUsers.addAll(taskUpdate.getUsersAdd());
            task.setAssignedUsers(currentUsers.stream().distinct().toList()); // Ensure no duplicates
        }


        if (task.getIsCompleted() != null && !task.getIsCompleted()) {
            task.setIsCompleted(taskUpdate.getIsCompleted());
        };

        taskRepo.save(task);

        return ResponseEntity.ok("Task updated successfully");
    }

    public ResponseEntity<String> updateProjectInService(ProjectUpdate projectUpdate, int orgId, int projectId) {
        // Get the list of task IDs to mark as completed
        List<Integer> completedTaskIds = projectUpdate.getTasks();

        if (completedTaskIds == null || completedTaskIds.isEmpty()) {
            return ResponseEntity.badRequest().body("No tasks provided for completion.");
        }

        // Find all tasks belonging to the project and organization with the given IDs
        List<Task> tasks = taskRepo.findByProjectOrganisationIdAndProjectIdAndIdIn(orgId, projectId, completedTaskIds);

        if (tasks.isEmpty()) {
            return ResponseEntity.badRequest().body("No matching tasks found.");
        }

        // Mark tasks as completed
        tasks.forEach(task -> task.setIsCompleted(true));

        taskRepo.saveAll(tasks);

        return ResponseEntity.ok("Tasks marked as completed successfully.");
    }

    public ResponseEntity<String> deleteProjects(DeleteProject deleteProject, int orgId) {
        List<Integer> removeProjects = deleteProject.getProjectId();

        if (removeProjects == null || removeProjects.isEmpty()) {
            return ResponseEntity.badRequest().body("No projects provided for deletion.");
        }

        // Fetch projects by orgId and projectIds
        List<Projects> projects = projectRepo.findByOrganisationIdAndIdIn(orgId, removeProjects);

        if (projects.isEmpty()) {
            return ResponseEntity.badRequest().body("No matching projects found.");
        }

        // Remove users associated with these projects
        projects.forEach(project -> {
            List<Task> tasks = project.getTasks();
            tasks.forEach(task -> task.getAssignedUsers().clear());
        });

        // Delete the projects
        projectRepo.deleteAll(projects);
        return ResponseEntity.ok("Projects and associated user assignments deleted successfully.");
    }

}

