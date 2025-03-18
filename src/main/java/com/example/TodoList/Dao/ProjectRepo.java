package com.example.TodoList.Dao;

import com.example.TodoList.Model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepo extends JpaRepository<Projects, Integer> {

    List<Projects> findAllById(Integer organisationId);

    List<Projects> findByOrganisationIdAndIdIn(int orgId, List<Integer> projectIds);

}
