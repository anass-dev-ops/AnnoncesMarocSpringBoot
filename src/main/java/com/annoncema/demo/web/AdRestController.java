package com.annoncema.demo.web;

import com.annoncema.demo.dao.AdRepository;
import com.annoncema.demo.dao.TypeODRepository;
import com.annoncema.demo.dao.TypePPRepository;
import com.annoncema.demo.entities.Ad;
import com.annoncema.demo.entities.TypeOD;
import com.annoncema.demo.entities.TypePP;
import com.annoncema.demo.services.AdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
public class AdRestController {
    @Autowired
    private AdService adService;
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private TypeODRepository typeODRepository;
    @Autowired
    private TypePPRepository typePPRepository;

    /* ====== Get different Methods for Recuperer Les Ads */
    @GetMapping(value = "/ads/maroc")
    public Page<Ad> adList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size) {
        return adService.findAllByState(page, size);
    }
    // ==== ADMIN ======
    @GetMapping(value = "/adminads")
    public Page<Ad> adListAdmin(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size) {
        return adService.findAll(page, size);
    }
    @GetMapping(value = "/ads/maroc/{categoryName}")
    public List<Ad> adListByCta(@PathVariable String categoryName) {
        return adService.findAdByCategoryName(categoryName);
    }
    @GetMapping(value = "/ads/{cityName}")
    public List<Ad> adListByCity(@PathVariable String cityName) {
        return adService.findByCityNameAndTitleContainsAndState(cityName, "", true);
    }
    @GetMapping(value = "/ads/{cityName}/{categoryName}")
    public List<Ad> adListByCatAndCit(@PathVariable String cityName,
                                      @PathVariable String categoryName) {
        return adService.findByCategoryNameAndCityNameAndTitleContainsAndState(categoryName, cityName, "", true);
    }


    /* ========= Searching ========*/
    @GetMapping(value = "/ads/search/maroc/{motCle}")
    public List<Ad> adListByMotCle(@PathVariable String motCle) {
        return adRepository.findByTitleContainsAndState(motCle, true);
    }
    @GetMapping(value = "/ads/search/{cityName}/{motCle}")
    public List<Ad> adListByCityAndMC(@PathVariable String cityName,
                                      @PathVariable()  String motCle) {
        return adRepository.findByCategoryNameAndTitleContainsAndState(cityName, motCle, true);
    }
    @GetMapping(value = "/ads/search/maroc/{categoryName}/{motCle}")
    public List<Ad> adListByCatAndMC(@PathVariable String categoryName,
                                     @PathVariable()  String motCle) {
        return adRepository.findByCategoryNameAndTitleContains(categoryName, motCle);
    }
    @GetMapping(value = "/ads/search/")
    public List<Ad> adListByCatAndCitAndMC(@RequestParam(value = "cityName", defaultValue = "") String cityName,
                                           @RequestParam(value = "categoryName", defaultValue = "")  String categoryName,
                                           @RequestParam(value = "motCle", defaultValue = "")  String motCle) {
        // if categoryName = demandes ......
        System.out.println(cityName + " === " + categoryName + " === " + motCle);
        return adRepository.findByCategoryNameAndCityNameAndTitleContainsAndState(categoryName, cityName, motCle, true);
    }



    // ===== Ad CRUD SYSTEM ===========
    @GetMapping(value = "/ads/byid/{id}")
    public Ad getAd(@PathVariable Long id) {
        return adService.getAdById(id);
    }

    /*@PostMapping(value = "/ads")
    public Ad saveAd(@RequestBody Ad ad) {
        return adService.saveAd(ad);
    }*/
    @PostMapping(value = "/ads")
    public Ad saveAd(@RequestParam(value = "files", required = false) MultipartFile files,
                     @RequestParam("ad") String ad) throws IOException {
        Ad ad1 = new ObjectMapper().readValue(ad, Ad.class);
        if (files != null) {
            String fileName = saveImageAd(files);
            ad1.setImage(fileName);
        }
        return adService.saveAd(ad1);
    }

    @PutMapping(value = "/ads")
    public Ad updateAd(@RequestBody Ad ad) {
        return adService.saveAd(ad);
    }

    @DeleteMapping(value = "/ads/{id}")
    public void deleteAd(@PathVariable Long id) {
        adService.deleteAdById(id);
    }
    // ===== Ad CRUD SYSTEM ===========

    // ===== Getters of Types ==========
    @GetMapping(value = "/typeod")
    public List<TypeOD> typeODList() {
        return typeODRepository.findAll();
    }
    @GetMapping(value = "/typepp")
    public List<TypePP> typePPList() {
        return typePPRepository.findAll();
    }

    // ==== Ad By Category Parent =====
    @GetMapping(value = "/ads/maroc/cp/{categoryParentName}")
    public List<Ad> getAdByCategoryParent(@PathVariable String categoryParentName) {
        return adRepository.findByCategoryCategoryParentNameAndState(categoryParentName, true);
    }
    @GetMapping(value = "/ads/cp/{cityName}/{categoryParentName}")
    public List<Ad> adListByCatPAndCit(@PathVariable String cityName,
                                      @PathVariable String categoryParentName) {
        return adRepository.findByCategoryCategoryParentNameAndCityNameAndState(categoryParentName, cityName, true);
    }


/*    public List<String> saveImagesAd(MultipartFile[] files) {
        List<String> fileNames = new ArrayList<>();
        Arrays.asList(files).stream().forEach(file -> {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Files.copy(file.getInputStream(), Paths.get(System.getProperty("user.home") + "/ResourcesAnnoncesMa/ImagesAds/Ad1" + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            fileNames.add(fileName);
        });
        return fileNames;
    }*/


    @GetMapping(path = "/ads/images/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageCategoryParent(@PathVariable String imageName) throws IOException {
        File file = new File(System.getProperty("user.home") + "/ResourcesAnnoncesMa/ImagesAds/" + imageName );
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    public String saveImageAd(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Files.copy(file.getInputStream() , Paths.get(System.getProperty("user.home") + "/ResourcesAnnoncesMa/ImagesAds/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }


    // Ads Of User
    @GetMapping(value = "/ads")
    public List<Ad> adListByUser(@RequestParam("username") String username) {
        return adService.findByAppUserUsername(username);
    }
}
