package com.example.TodoList.Dao;

import com.example.TodoList.Model.Organisation;
import com.example.TodoList.Model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganisationRepo extends JpaRepository<Organisation, Long> {

    List<Projects> findAllById(Integer organisationId);
}
