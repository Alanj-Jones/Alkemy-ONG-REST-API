package com.alkemy.java.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.alkemy.java.dto.CategoriesDtoFull;
import com.alkemy.java.model.Categories;
import com.alkemy.java.service.CategoriesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Categories Controller", description = "Controlador con los endpoints que actúan sobre las categorías.")
public class CategoriesController {

    private Map<String,Object> response = new HashMap<>();

    private static final String OK_RESPONSE = "Operacion realizada con exito.";
    private static final String CREATED_RESPONSE = "Categoria creada con exito.";
    private static final String UNAUTHORIZED_RESPONSE = "Necesita autorizacion para realizar esta operacion..";
    private static final String FORBIDDEN_RESPONSE = "No tiene los permisos necesarios para realizar esta operacion.";
    private static final String NOT_FOUND_RESPONSE = "El recurso buscado no existe o no se encuentra disponible.";

    @Autowired
    private CategoriesService categoriesService;

    @ApiOperation(value = "${categories.api.operation.allCategories}", response = ResponseEntity.class)
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = OK_RESPONSE),
        @ApiResponse(code = 401, message = UNAUTHORIZED_RESPONSE),
        @ApiResponse(code = 403, message = FORBIDDEN_RESPONSE),
        @ApiResponse(code = 404, message = NOT_FOUND_RESPONSE)
    })
    @GetMapping("/categories")
    public ResponseEntity<?> allCategories(@RequestParam(required = false, defaultValue="0")int page) {        
                
        return ResponseEntity.ok(categoriesService.getCategoriesAsDto(page));
    }

    @ApiOperation(value = "${categories.api.operation.getCategory}", response = ResponseEntity.class)
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = OK_RESPONSE),
        @ApiResponse(code = 401, message = UNAUTHORIZED_RESPONSE),
        @ApiResponse(code = 403, message = FORBIDDEN_RESPONSE),
        @ApiResponse(code = 404, message = NOT_FOUND_RESPONSE)
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/categories/{id}")
    public ResponseEntity<?> getCategory(@ApiParam(value = "${categories.api.param.getCategory}", required = true) @PathVariable Long id){
        return new ResponseEntity<>(categoriesService.findById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "${categories.api.operation.updateCategories}", response = ResponseEntity.class)
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = OK_RESPONSE),
        @ApiResponse(code = 401, message = UNAUTHORIZED_RESPONSE),
        @ApiResponse(code = 403, message = FORBIDDEN_RESPONSE),
        @ApiResponse(code = 404, message = NOT_FOUND_RESPONSE)
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PutMapping("/categories/{id}")
    public ResponseEntity<?>  updateCategories(@PathVariable Long id, @RequestBody CategoriesDtoFull categoriesDto){
        categoriesService.updateCategories(id,categoriesDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("")
                .buildAndExpand(id)
                .toUri();
        return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
    }

    @ApiOperation(value = "${categories.api.operation.addCategory}", response = ResponseEntity.class)
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = OK_RESPONSE),
        @ApiResponse(code = 201, message = CREATED_RESPONSE),
        @ApiResponse(code = 401, message = UNAUTHORIZED_RESPONSE),
        @ApiResponse(code = 403, message = FORBIDDEN_RESPONSE),
        @ApiResponse(code = 404, message = NOT_FOUND_RESPONSE)
    })
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    @PostMapping("/categories")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Categories category) {
        return categoriesService.createCategory(category);
    }

    @ApiOperation(value = "${categories.api.operation.deleteCategory}", response = ResponseEntity.class)
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = OK_RESPONSE),
        @ApiResponse(code = 401, message = UNAUTHORIZED_RESPONSE),
        @ApiResponse(code = 403, message = FORBIDDEN_RESPONSE),
        @ApiResponse(code = 404, message = NOT_FOUND_RESPONSE)
    })
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> DeleteCategory(@ApiParam(value = "${categories.api.param.deleteCategory}", required = true) @PathVariable Long id){
        categoriesService.deleteCategoryById(id);
        response.put("message", "Eliminado con éxito");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}

