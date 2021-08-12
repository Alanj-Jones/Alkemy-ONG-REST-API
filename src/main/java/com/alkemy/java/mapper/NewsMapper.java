package com.alkemy.java.mapper;

import com.alkemy.java.dto.response.NewsResponseUpdateDto;
import com.alkemy.java.model.News;
import org.springframework.stereotype.Component;

@Component
public class NewsMapper {

    CategoriesMapper categoriesMapper = new CategoriesMapper();

    public NewsResponseUpdateDto convertDto(News news){
        NewsResponseUpdateDto newsResponseUpdateDtoDto = new NewsResponseUpdateDto();
        newsResponseUpdateDtoDto.setName(news.getName());
        newsResponseUpdateDtoDto.setContent(news.getContent());
        newsResponseUpdateDtoDto.setImage(news.getImage());
        newsResponseUpdateDtoDto
                .setCategories(news.getCategories().getName());
        return newsResponseUpdateDtoDto;
    }


}
