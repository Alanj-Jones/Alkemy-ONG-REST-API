package com.alkemy.java.service.impl;

import com.alkemy.java.controller.NewsController;
import com.alkemy.java.exception.news.NewsExistsException;
import com.alkemy.java.dto.request.NewsRequestUpdateDto;
import com.alkemy.java.dto.response.NewsResponseUpdateDto;
import com.alkemy.java.mapper.NewsMapper;
import com.alkemy.java.model.Categories;
import com.alkemy.java.model.News;
import com.alkemy.java.repository.CategoriesRepository;
import com.alkemy.java.repository.NewsRepository;
import com.alkemy.java.service.NewsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService{

    @Autowired
    private MessageSource messageSource;

    private Logger logger = LoggerFactory.getLogger(CategoriesServiceImpl.class);

    @Value("${page.size}")
    private int pageSize;


    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    private static final String NEWS= "news";


    @Override
    public ResponseEntity<?> deleteById(Long id) {
        try {
            newsRepository.deleteById(id);
            return new ResponseEntity<>( messageSource.getMessage("news.delete.success", null,null), HttpStatus.OK);
        } catch (DataAccessException e) {
            return new ResponseEntity<>(  messageSource.getMessage("news.id.not.found", new Object[] {id}, null), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public News save(News news) throws NewsExistsException {
        if(newsRepository.findByName(news.getName()) == null){
            news.setCategories(categoriesRepository.findByName(NEWS));
            return newsRepository.save(news);
        }
        else {
            throw new NewsExistsException();
        }
    }

    @Override
    public News findById(Long id) {
        return  newsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,
                        messageSource.getMessage("news.id.not.found", new Object[] {id}, null)));
    }

    @Override
    public NewsResponseUpdateDto updateNews(Long id, NewsRequestUpdateDto newsRequestUpdateDto) {

        News news = findById(id);

        try {
            if (newsRequestUpdateDto.getName() != null) {
                news.setName(newsRequestUpdateDto.getName());
            }
            if (newsRequestUpdateDto.getContent() != null) {
                news.setContent(newsRequestUpdateDto.getContent());
            }
            if (newsRequestUpdateDto.getImage() != null) {
                news.setImage(newsRequestUpdateDto.getImage());
            }

            if (newsRequestUpdateDto.getCategoriesId() != null) {
                Categories categories = categoriesRepository.findById(newsRequestUpdateDto.getCategoriesId())
                        .orElseThrow(() -> new ResponseStatusException
                                (HttpStatus.NOT_FOUND, messageSource.getMessage("category.not.found", null, null)));

                news.setCategories(categories);

            }
            newsRepository.save(news);
        } catch (DataAccessException dataAccessException) {
            logger.error(dataAccessException.getMessage());
        }

        NewsMapper newsMapper = new NewsMapper();
        return newsMapper.convertDto(news);
    }

    @Override
    public CollectionModel<News> findAllNewsPag(int pag) {

       Pageable pageable = PageRequest.of(pag, pageSize);
       Page<News> pageNews = newsRepository.findAll(pageable);
       List<Link> links = new ArrayList<>();

       if(pageNews.getContent().isEmpty()){ throw new ResponseStatusException(HttpStatus.NO_CONTENT); }
       links.add(linkTo(methodOn(NewsController.class).getNewsPage(pag)).withSelfRel());
       if(pageNews.hasPrevious()){
           links.add(linkTo(methodOn(NewsController.class)
                   .getNewsPage(pag - 1)).withRel("prev"));
       }
        if(pageNews.hasNext()){
            links.add(linkTo(methodOn(NewsController.class)
                    .getNewsPage(pag + 1)).withRel("next"));
        }
        return CollectionModel.of(pageNews, links);
    }

}
