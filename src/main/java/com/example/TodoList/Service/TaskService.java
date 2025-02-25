package com.example.TodoList.Service;
import com.example.TodoList.Dao.TaskRepo;
import com.example.TodoList.Dao.UserRepo;
import com.example.TodoList.Dto.TaskRequest;
import com.example.TodoList.Model.Task;
import com.example.TodoList.Model.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    UserRepo userRepo;
    public String createTask(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepo.findByUsername(username);
        User user = userOptional.get();
        task.setUser(user);
        user.getTasks().add(task);
        taskRepo.save(task);
        return "Created Task Successfully";
    }
    public String updateTask(Integer taskId, TaskRequest taskRequest) {
        Optional<Task> taskOptional = taskRepo.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setUpdatedAt(LocalDateTime.now());
            task.setTaskDescription(taskRequest.getTaskdesc());
            taskRepo.save(task);
            return "Updated Task Successfully";
        } else {
            return "Task not found";
        }
    }
    public List<Task> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return taskRepo.findAll();
    }
    public String deleteTask(Integer taskId) {
        Optional<Task> taskOptional = taskRepo.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            taskRepo.delete(task);
            return "Deleted Task Successfully";
        } else {
            return "Task not found";
        }
    }
}