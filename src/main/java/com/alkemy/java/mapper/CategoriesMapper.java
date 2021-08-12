package com.alkemy.java.mapper;

import com.alkemy.java.dto.CategoriesDtoName;
import com.alkemy.java.model.Categories;
import org.springframework.stereotype.Component;

@Component
public class CategoriesMapper {
    private String name;

    public CategoriesDtoName convertToDto(Categories categories) {
        final CategoriesDtoName categoriesDtoName = new CategoriesDtoName() {
            @Override
            public String getName() {
                return name = "";
            }
        };
        return categoriesDtoName;
    }
}
