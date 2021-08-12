package com.alkemy.java.controller;

import com.alkemy.java.model.News;
import com.alkemy.java.dto.request.NewsRequestUpdateDto;
import com.alkemy.java.service.NewsService;

import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value="Novedades")
public class NewsController {

    private static final String CREATE = "Create";
    private static final int CODE_CREATE = 201;
    private static final String NO_CONTENT = "No content";
    private static final int CODE_NO_CONTENT = 204;


    @Autowired
    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService){
        this.newsService=newsService;
    }



    @ApiResponses( value = {
            @ApiResponse( code = CODE_CREATE , message = CREATE )}
    )
    @ApiOperation(value = "${newsController.create}")
    @ApiParam(name = "name" , example = "titulo..",required = true)
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/news")
    public ResponseEntity News(@RequestBody News news) throws Exception {
        News news1 = newsService.save(news);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(news1.getId())
                .toUri();
        return new ResponseEntity<>(location, (HttpStatus.CREATED));
    }

    @ApiOperation(value = "${newsController.delete}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable(name = "id") Long id) {
        return newsService.deleteById(id);
    }
    @ApiOperation(value = "${newsController.update}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PutMapping("/news/{id}")
    public ResponseEntity<?> updateNews(@PathVariable Long id,
                              @RequestBody(required = false) NewsRequestUpdateDto newsRequest){
        return new ResponseEntity<>(newsService.updateNews(id, newsRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "${newsController.search}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/news/{id}")
    public ResponseEntity<?> getNewsNews(@PathVariable Long id){
        return new ResponseEntity<>(newsService.findById(id), HttpStatus.OK);
    }

    @ApiResponses( value = {
            @ApiResponse( code = CODE_NO_CONTENT , message = NO_CONTENT )}
    )
    @ApiOperation(value = "${newsController.pag}", notes = "${newsController.pag.note}" )
    @GetMapping("/news")
    public ResponseEntity<?> getNewsPage(@RequestParam(name = "page",defaultValue = "0") int page){
        return new ResponseEntity<>(newsService.findAllNewsPag(page),HttpStatus.OK);
    }

}
