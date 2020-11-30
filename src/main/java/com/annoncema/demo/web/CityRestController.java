package com.annoncema.demo.web;

import com.annoncema.demo.dao.CategoryRepository;
import com.annoncema.demo.dao.CityRepository;
import com.annoncema.demo.entities.Category;
import com.annoncema.demo.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CityRestController {
    @Autowired
    private CityRepository cityRepository;

    @GetMapping(value = "/cities")
    public List<City> citiesList() {
        return cityRepository.findAll();
    }
    @PostMapping(value = "/saveCity")
    public City saveCity(@RequestBody City city) {
        return cityRepository.save(city);
    }
    @PutMapping(value = "/updateCity")
    public City updateCity(@RequestBody City city) {
        return cityRepository.save(city);
    }
    @DeleteMapping(value = "/deleteCity/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityRepository.deleteById(id);
    }
}
