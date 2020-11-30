package com.annoncema.demo.services;

import com.annoncema.demo.entities.CategoryParent;

import java.util.List;

public interface CategoryParentService {
    // Read Data
    public List<CategoryParent> findAll();
    public CategoryParent findByName(String categoryParentName);
    // Actions for Data
    public CategoryParent saveCategoryParent(CategoryParent categoryParent);
    public CategoryParent updateCategoryParent(CategoryParent categoryParent);
    public void deleteCategoryParentById(Long id);
}
