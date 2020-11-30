package com.annoncema.demo.web;

import com.annoncema.demo.dao.CategoryParentRepository;
import com.annoncema.demo.dao.CategoryRepository;
import com.annoncema.demo.entities.Category;
import com.annoncema.demo.entities.CategoryParent;
import com.annoncema.demo.services.CategoryParentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class CategoryRestController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryParentService categoryParentService;

    // private final String URL_CAT = "/categories";
    // private final String URL_CAT_P = "/categoryParents";

    // ===== CRUD System Category ======
    @GetMapping(value = "/categories")
    public List<Category> categoryList() {
        return categoryRepository.findAll();
    }
    @PostMapping(value = "/saveCategory")
    public Category saveCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
    @PutMapping(value = "/updateCategory")
    public Category updateCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }
    @DeleteMapping(value = "/deleteCategory/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }

    // ===== CRUD System CategoryParent ======
    @GetMapping(value = "/categoryParents")
    public List<CategoryParent> categoryParentList() {
        return categoryParentService.findAll();
    }
    @PostMapping(value = "/saveCategoryParent")
    public CategoryParent saveCategoryParent(@RequestParam("categoryParent") String categoryParent,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        // System.out.println("=======: " + categoryParent);
        String fileName = saveImageCategoryParent(file);
        CategoryParent categoryParent1 = new ObjectMapper().readValue(categoryParent, CategoryParent.class);
        categoryParent1.setImage(fileName.replace(" ", "_"));
        return categoryParentService.saveCategoryParent(categoryParent1);
    }
    @PutMapping(value = "/updateCategoryParent")
    public CategoryParent updateCategoryParent(@RequestBody CategoryParent categoryParent) {
        return categoryParentService.updateCategoryParent(categoryParent);
    }
    @DeleteMapping(value = "/deleteCategoryParent" + "/{id}")
    public void deleteCategoryParent(@PathVariable Long id) {
        categoryParentService.deleteCategoryParentById(id);
    }
    // ==== Query ====
    @GetMapping(value = "/categories/{categoryParentName}")
    public List<Category> getCategoriesByCP(@PathVariable String categoryParentName) {
        return categoryRepository.findByCategoryParentName(categoryParentName);
    }
    // ==== Management of Images Categories Parents ======
    @GetMapping(path = "/ads/categoryParents/images/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImageCategoryParent(@PathVariable String imageName) throws IOException {
        File file = new File(System.getProperty("user.home") + "/ResourcesAnnoncesMa/ImagesCatPar/" + imageName );
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }
    // @PostMapping(path = "/ads/categoryParents/images", produces = MediaType.IMAGE_PNG_VALUE)
    public String saveImageCategoryParent(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Files.copy(file.getInputStream() , Paths.get(System.getProperty("user.home") + "/ResourcesAnnoncesMa/ImagesCatPar/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
