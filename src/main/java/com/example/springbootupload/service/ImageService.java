package com.example.springbootupload.service;

import com.example.springbootupload.model.ImageEntity;

public interface ImageService {
    Iterable<ImageEntity> getAll();
    ImageEntity saveData(ImageEntity post);
    ImageEntity getById(Long id);
    void deleteById(Long id);
}
