package com.annoncema.demo.dao;

import com.annoncema.demo.entities.TypePP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypePPRepository extends JpaRepository<TypePP, Long> {
    public TypePP findByTypeName(String typeName);
}
