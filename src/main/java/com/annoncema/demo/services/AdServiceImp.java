package com.annoncema.demo.services;

import com.annoncema.demo.dao.AdRepository;
import com.annoncema.demo.entities.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdServiceImp implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Override
    public List<Ad> findAdByCategoryName(String categoryName) {
        return adRepository.findByCategoryNameAndState(categoryName, true);
    }

    @Override
    public List<Ad> findByCityNameAndTitleContainsAndState(String cityName, String motCe, boolean state) {
        return adRepository.findByCityNameAndTitleContainsAndState(cityName, motCe, true);
    }

    @Override
    public List<Ad> findByCategoryNameAndCityNameAndTitleContainsAndState(String categoryName, String CityName, String motCle, boolean state) {
        return adRepository.findByCategoryNameAndCityNameAndTitleContainsAndState(categoryName, CityName, motCle, true);
    }

    @Override
    public List<Ad> findByAppUserUsername(String username) {
        return adRepository.findByAppUserUsername(username);
    }


    @Override
    public Page<Ad> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adRepository.findAll(pageable);
    }

    @Override
    public Page<Ad> findAllByState(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adRepository.findByState(true, pageable);
    }

    @Override
    public Ad saveAd(Ad ad) {
        return adRepository.save(ad);
    }

    @Override
    public Ad getAdById(Long id) {
        return adRepository.findById(id).get();
    }

    @Override
    public void deleteAdById(Long id) {
        adRepository.deleteById(id);
    }


}
