package com.alkemy.java.service;

import java.util.List;
import java.util.Map;

import com.alkemy.java.model.Categories;
import com.alkemy.java.dto.CategoriesDtoFull;
import com.alkemy.java.dto.CategoriesDtoName;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface CategoriesService {


    
    CollectionModel<CategoriesDtoName> getCategoriesAsDto(int page);

    Categories findById(Long id);

    void updateCategories(Long id, CategoriesDtoFull categoriesDtoFull);
    ResponseEntity<?> createCategory(Categories category);
    
    void deleteCategoryById(Long id);
}
