package com.alkemy.java.repository;

import com.alkemy.java.dto.CategoriesDtoName;
import com.alkemy.java.model.Categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories,Long> {
    public Categories findByName(String name);

   Page<CategoriesDtoName> findAllByOrderByName(Pageable pageable);
}
