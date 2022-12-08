package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Category;
import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findById(Integer id);
}
