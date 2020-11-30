package com.annoncema.demo.dao;

import com.annoncema.demo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;


public interface CityRepository extends JpaRepository<City, Long> {
    public City findByName(String cityName);
}
