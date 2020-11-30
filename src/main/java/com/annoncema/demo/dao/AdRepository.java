package com.annoncema.demo.dao;

import com.annoncema.demo.entities.Ad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Long> {
    public List<Ad> findByTitleContainsAndState(String motCle, boolean state);

    public Page<Ad> findByState(boolean state,Pageable pageable);
    public List<Ad> findByCategoryNameAndState(String categoryName, boolean state);
    public List<Ad> findByCategoryNameAndTitleContainsAndState(String categoryName, String motCe, boolean state);
    public List<Ad> findByCityNameAndTitleContainsAndState(String cityName, String motCe, boolean state);
    public List<Ad> findByCategoryNameAndCityNameAndTitleContainsAndState(String categoryName, String CityName, String motCle, boolean state);

    public List<Ad> findByCategoryCategoryParentNameAndState(String catgoryParentName, boolean state);
    public List<Ad> findByCategoryCategoryParentNameAndCityNameAndState(String catgoryParentName, String cityName, boolean state);



    public Page<Ad> findAll(Pageable pageable);
    public List<Ad> findByTitleContains(String motCle);
    public List<Ad> findByCategoryName(String categoryName);
    public List<Ad> findByCategoryNameAndTitleContains(String categoryName, String motCe);
    public List<Ad> findByCityNameAndTitleContains(String cityName, String motCe);
    public List<Ad> findByCategoryNameAndCityNameAndTitleContains(String categoryName, String CityName, String motCle);

    public List<Ad> findByCategoryCategoryParentName(String catgoryParentName);
    public List<Ad> findByCategoryCategoryParentNameAndCityName(String catgoryParentName, String cityName);

    public List<Ad> findByAppUserUsername(String username);
}
