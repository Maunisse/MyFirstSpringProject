package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;

@Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image getImageId(int id){
        Optional<Image> optionalImage=imageRepository.findById(id);
        return optionalImage.orElse(null);
    }
    @Transactional
    public void deleteImage(int id){
        imageRepository.deleteById(id);
    }

    @Transactional
    public void updateImage(int id, Image image){
        image.setId(id);
        imageRepository.save(image);
    }
}
