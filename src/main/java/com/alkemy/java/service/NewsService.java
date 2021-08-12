package com.alkemy.java.service;

import com.alkemy.java.exception.news.NewsExistsException;
import com.alkemy.java.dto.request.NewsRequestUpdateDto;
import com.alkemy.java.dto.response.NewsResponseUpdateDto;
import com.alkemy.java.model.News;

import org.springframework.hateoas.CollectionModel;

import org.springframework.http.ResponseEntity;

public interface NewsService {
    ResponseEntity<?> deleteById(Long id);
    News save (News news) throws NewsExistsException;
    News findById(Long id);
    NewsResponseUpdateDto updateNews(Long id, NewsRequestUpdateDto newsRequestUpdateDto);
    CollectionModel<News> findAllNewsPag(int pag);

}
