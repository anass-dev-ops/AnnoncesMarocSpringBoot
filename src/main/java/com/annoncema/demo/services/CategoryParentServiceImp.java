package com.annoncema.demo.services;

import com.annoncema.demo.dao.CategoryParentRepository;
import com.annoncema.demo.entities.CategoryParent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
@Service
public class CategoryParentServiceImp implements CategoryParentService {
    @Autowired
    private CategoryParentRepository categoryParentRepository;

    @Override
    public List<CategoryParent> findAll() {
        return categoryParentRepository.findAll();
    }

    @Override
    public CategoryParent findByName(String categoryParentName) {
        return categoryParentRepository.findByName(categoryParentName);
    }

    @Override
    public CategoryParent saveCategoryParent(CategoryParent categoryParent) {
        //String image = categoryParent.getName().replace(" ", "_") + ".png";
        //categoryParent.setImage(image);
        return categoryParentRepository.save(categoryParent);
    }

    @Override
    public CategoryParent updateCategoryParent(CategoryParent categoryParent) {
        return categoryParentRepository.save(categoryParent);
    }

    @Override
    public void deleteCategoryParentById(Long id) {
        categoryParentRepository.deleteById(id);
    }
}
