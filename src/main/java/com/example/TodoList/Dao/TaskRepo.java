package com.example.TodoList.Dao;

import com.example.TodoList.Model.Task;
import com.example.TodoList.Model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
  @Query("SELECT t FROM Task t WHERE LOWER(t.taskName) = LOWER(:taskname) AND t.user.id = :userId")
  Optional<Task> findByUserIdAndTaskName(@Param("userId") Integer userId, @Param("taskname") String taskname);

  List<Task> findByUser(User user);
}
