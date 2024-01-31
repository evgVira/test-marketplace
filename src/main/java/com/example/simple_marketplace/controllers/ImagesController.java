package com.example.simple_marketplace.controllers;

import com.example.simple_marketplace.models.Images;
import com.example.simple_marketplace.repositories.ImagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
public class ImagesController {
    private final ImagesRepository imagesRepository;

    @Autowired
    ImagesController(ImagesRepository imagesRepository){
        this.imagesRepository = imagesRepository;
    }

    @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable(value = "id")Long id){
        Images images = imagesRepository.findById(id).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", images.getOriginalFileName())
                .contentType(MediaType.valueOf(images.getContentType()))
                .contentLength(images.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(images.getBytes())));
    }
}
