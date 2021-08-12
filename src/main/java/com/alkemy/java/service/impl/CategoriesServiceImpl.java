package com.alkemy.java.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.alkemy.java.controller.CategoriesController;
import com.alkemy.java.dto.CategoriesDtoFull;
import com.alkemy.java.dto.CategoriesDtoName;
import com.alkemy.java.model.Categories;
import com.alkemy.java.repository.CategoriesRepository;
import com.alkemy.java.service.CategoriesService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;



@Service
public class CategoriesServiceImpl implements CategoriesService{

    private Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);

    private static final String ONLY_LETTERS = "[a-zA-ZáéíóúüñÁÉÍÓÚÜÑ ]+";

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Value("${spring.data.web.pageable.default-page-size}")
    private int size;

    @Value("${page.not-found}")
    private String notFoundMessage;

    @SuppressWarnings("all")
    @Override
    public CollectionModel<CategoriesDtoName> getCategoriesAsDto(int page) {

        List<Link> links = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<CategoriesDtoName> categoriesPage = categoriesRepository.findAllByOrderByName(pageable);

        if (categoriesPage.hasContent()) {

            if (categoriesPage.hasPrevious()) {
                links.add(linkTo(methodOn(CategoriesController.class)
                        .allCategories(categoriesPage.previousPageable().getPageNumber())).withRel("prev"));
            }

            if (categoriesPage.hasNext()) {
                links.add(linkTo(methodOn(CategoriesController.class)
                        .allCategories(categoriesPage.nextPageable().getPageNumber())).withRel("next"));
            }

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, notFoundMessage + page);
        }

        links.add(linkTo(methodOn(CategoriesController.class).allCategories(page)).withSelfRel());

        return CollectionModel.of(categoriesPage, links);

    }

    @Override
    @Transactional(readOnly = true)
    public Categories findById(Long id) {
        return categoriesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void updateCategories(Long id, CategoriesDtoFull categoriesDto){

        Categories category= this.findById(id);
        List<CategoriesDtoFull> list=new ArrayList<CategoriesDtoFull>();
        list.add(categoriesDto);
        for ( CategoriesDtoFull c: list){
            if (c.getName()!=null) {
                category.setName(categoriesDto.getName());
            }
            if (c.getImage()!=null){
                category.setImage(categoriesDto.getImage());
            }
            if (c.getDescription()!=null){
                category.setDescription(categoriesDto.getDescription());
            }
        }
        categoriesRepository.save(category);
    }

    @Override
    public ResponseEntity<?> createCategory(Categories category) {
        if (validOnlyLetters(category.getName())) {
            categoriesRepository.save(category);
            return ResponseEntity.ok("Categoría creada con éxito.");
        }
        return new ResponseEntity<>("El campo 'name' debe contener únicamente letras", HttpStatus.UNPROCESSABLE_ENTITY);
    }
    
    private boolean validOnlyLetters (String value) {
        return Pattern.matches(ONLY_LETTERS, value);
      }

    @Override
    public void deleteCategoryById(Long id){
        try{
            categoriesRepository.deleteById(id);
        }catch(DataAccessException dataAccess){
            logger.error(dataAccess.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la categoría");
        }
    }

}
