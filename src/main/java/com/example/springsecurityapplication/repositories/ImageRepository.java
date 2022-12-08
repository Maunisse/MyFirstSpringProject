package com.example.springsecurityapplication.repositories;

import com.example.springsecurityapplication.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {

}
