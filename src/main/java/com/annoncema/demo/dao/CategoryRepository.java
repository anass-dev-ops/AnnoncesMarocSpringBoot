package com.annoncema.demo.dao;

import com.annoncema.demo.entities.Category;
import com.annoncema.demo.entities.CategoryParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Category findByName(String categoryName);
    public List<Category> findByCategoryParentName(String categoryParentName);
}
