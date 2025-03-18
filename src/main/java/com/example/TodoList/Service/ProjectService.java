package com.example.TodoList.Service;


import com.example.TodoList.Dao.OrganisationRepo;
import com.example.TodoList.Dao.ProjectRepo;
import com.example.TodoList.Dao.TaskRepo;
import com.example.TodoList.Dto.TaskUpdate;
import com.example.TodoList.Model.Organisation;
import com.example.TodoList.Model.Projects;
import com.example.TodoList.Model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private OrganisationRepo organisationRepo;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private TaskRepo taskRepo;

    public ResponseEntity<List<Task>> getTasksByProject(int projectId, int orgId) {
        List<Task> tasks = taskRepo.findByProjectOrganisationIdAndProjectId(orgId, projectId);

        if (tasks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tasks);
    }

    public ResponseEntity<Task> updateTask(int orgId, int projectId, int taskId, TaskUpdate taskUpdate) {
        Optional<Organisation> organisationOpt = organisationRepo.findById((long) orgId);
        if (organisationOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Optional<Projects> projectOpt = projectRepo.findById(projectId);
        if (projectOpt.isEmpty() || !projectOpt.get().getOrganisation().getId().equals((long) orgId)) {
            return ResponseEntity.notFound().build();
        }
        Optional<Task> taskOpt = taskRepo.findById(taskId);
        if (taskOpt.isEmpty() || !taskOpt.get().getProject().getId().equals(projectId)) {
            return ResponseEntity.notFound().build();
        }
        Task task = taskOpt.get();

        // Update task properties
        if (taskUpdate.getTaskDescription() != null) {
            task.setTaskDescription(taskUpdate.getTaskDescription());
        }

        if (taskUpdate.getIsCompleted() != null) {
            task.setIsCompleted(taskUpdate.getIsCompleted());
        }

        // Handle users addition and removal
        if (taskUpdate.getUsersRemove() != null) {
            task.getAssignedUsers().removeAll(taskUpdate.getUsersRemove());
        }

        if (taskUpdate.getUsersAdd() != null) {
            task.getAssignedUsers().addAll(taskUpdate.getUsersAdd());
        }

        // Save updated Task
        taskRepo.save(task);
        return ResponseEntity.ok(task);
    }

    public ResponseEntity<String> deleteTask(int orgId, int projectId, int taskId) {
        // Fetch Organisation
        Optional<Organisation> organisationOpt = organisationRepo.findById((long) orgId);
        if (organisationOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organisation not found");
        }

        // Fetch Project under Organisation
        Optional<Projects> projectOpt = projectRepo.findById(projectId);
        if (projectOpt.isEmpty() || !projectOpt.get().getOrganisation().getId().equals((long) orgId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found or does not belong to the organisation");
        }

        // Fetch Task under Project
        Optional<Task> taskOpt = taskRepo.findById(taskId);
        if (taskOpt.isEmpty() || !taskOpt.get().getProject().getId().equals(projectId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found or does not belong to the project");
        }

        // Delete Task
        taskRepo.deleteById(taskId);

        return ResponseEntity.ok("Task deleted successfully");
    }
}
