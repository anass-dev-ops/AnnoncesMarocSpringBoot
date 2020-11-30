package com.annoncema.demo;

import com.annoncema.demo.dao.*;
import com.annoncema.demo.entities.*;
import com.annoncema.demo.services.AdService;
import com.annoncema.demo.services.AuthenticationService;
import com.annoncema.demo.services.CategoryParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    @Autowired
    private AdService adService;
    @Autowired
    private CategoryParentService categoryParentService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private TypeODRepository typeODRepository;
    @Autowired
    private TypePPRepository typePPRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        // Users - Authentication
        Stream.of("ADMIN", "USER").forEach(roleName -> {
            AppRole appRole = new AppRole();
            appRole.setRoleName(roleName);
            authenticationService.saveRole(appRole);
        });

        Stream.of("omar", "ali", "yassin").forEach(username -> {
            AppUser appUser = new AppUser();
            appUser.setUsername(username);
            appUser.setPassword("1234");
            authenticationService.saveUser(appUser);
        });

        authenticationService.addRoleToUser(
                authenticationService.getRoleByRoleName("ADMIN"),
                authenticationService.getUserByUsername("omar"));
        authenticationService.addRoleToUser(
                authenticationService.getRoleByRoleName("USER"),
                authenticationService.getUserByUsername("omar"));
        authenticationService.addRoleToUser(
                authenticationService.getRoleByRoleName("USER"),
                authenticationService.getUserByUsername("ali"));
        authenticationService.addRoleToUser(
                authenticationService.getRoleByRoleName("USER"),
                authenticationService.getUserByUsername("yassin"));

        // ====== Ads ============================

        Stream.of("OFFRE", "DEMANDE").forEach(typeName -> {
            typeODRepository.save(new TypeOD(null, typeName, null));
        });

        Stream.of("PARTICULIER", "PROFESSIONNEL").forEach(typeName -> {
            typePPRepository.save(new TypePP(null, typeName, null));
        });

        Stream.of("Informatique et Multimedia", "Vehicules").forEach(categoryParentName -> {
            CategoryParent categoryParent = new CategoryParent();
            categoryParent.setName(categoryParentName);
            categoryParent.setImage(categoryParentName.replace(" ", "_")+".png");
            categoryParentService.saveCategoryParent(categoryParent);
        });

        Stream.of("Casablanca", "Rabat").forEach(cityName -> {
            City city = new City();
            city.setName(cityName);
            cityRepository.save(city);
        });

        Stream.of("Computers","Telephones").forEach(CatName -> {
            Category category = new Category();
            category.setName(CatName);
            category.setCategoryParent(categoryParentService.findByName("Informatique et Multimedia"));
            categoryRepository.save(category);
        });

        Stream.of("HP Compaq Pro SFF", "HP Compaq 6000", "Dell latitude", "Mac Pro")
                .forEach(adName -> {
                    Ad ad = new Ad();
                    ad.setState(true);
                    ad.setImage(adName.replace(" ", "_") + ".jpg");
                    ad.setAppUser(authenticationService.getUserByUsername("ali"));
                    ad.setTitle(adName);
                    ad.setDescription(adName + " Description D'aunnonce");
                    ad.setPrice(9000);
                    ad.setCreationDate(new Date());
                    ad.setCategory(categoryRepository.findByName("Computers"));
                    ad.setCity(cityRepository.findByName("Rabat"));
                    ad.setTypeOD(typeODRepository.findByTypeName("OFFRE"));
                    ad.setTypePP(typePPRepository.findByTypeName("PARTICULIER"));
                    adService.saveAd(ad);
                });

        Stream.of("Samsung j7", "Infinix", "iPhone 11", "Samsung S10")
                .forEach(adName -> {
                    Ad ad = new Ad();
                    ad.setImage(adName.replace(" ", "_") + ".jpg");
                    ad.setAppUser(authenticationService.getUserByUsername("yassin"));
                    // ad.setState(false);
                    ad.setTitle(adName);
                    ad.setDescription(adName + " Description D'aunnonce");
                    ad.setPrice(4000);
                    ad.setCreationDate(new Date());
                    ad.setCategory(categoryRepository.findByName("Telephones"));
                    ad.setCity(cityRepository.findByName("Casablanca"));
                    ad.setTypeOD(typeODRepository.findByTypeName("DEMANDE"));
                    ad.setTypePP(typePPRepository.findByTypeName("PROFESSIONNEL"));
                    adService.saveAd(ad);
                });




    }


}
