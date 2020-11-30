package com.annoncema.demo.dao;

import com.annoncema.demo.entities.CategoryParent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryParentRepository extends JpaRepository<CategoryParent, Long> {
    public CategoryParent findByName(String categoryParentName);
}
