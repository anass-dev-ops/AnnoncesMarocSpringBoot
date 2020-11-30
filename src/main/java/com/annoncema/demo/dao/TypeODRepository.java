package com.annoncema.demo.dao;

import com.annoncema.demo.entities.TypeOD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeODRepository extends JpaRepository<TypeOD, Long> {
    public TypeOD findByTypeName(String typeName);
}
