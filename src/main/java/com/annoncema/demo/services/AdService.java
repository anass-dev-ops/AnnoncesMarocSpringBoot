package com.annoncema.demo.services;

import com.annoncema.demo.entities.Ad;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdService {
    // Read Data
    public Page<Ad> findAll(int page, int size);
    public Page<Ad> findAllByState(int page, int size);
    public List<Ad> findAdByCategoryName(String categoryName);
    public List<Ad> findByCityNameAndTitleContainsAndState(String cityName, String motCe, boolean state);
    public List<Ad> findByCategoryNameAndCityNameAndTitleContainsAndState(String categoryName, String CityName, String motCle, boolean state);

    public List<Ad> findByAppUserUsername(String username);

    // Actions for Data
    public Ad saveAd(Ad ad);
    public Ad getAdById(Long id);
    public void deleteAdById(Long id);



}
