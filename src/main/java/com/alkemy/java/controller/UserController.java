package com.alkemy.java.controller;

import javax.servlet.http.HttpServletRequest;

import com.alkemy.java.dto.UserDTOtoUpdate;
import com.alkemy.java.model.User;
import com.alkemy.java.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

	   @Autowired
	    UserService userService;

	    @DeleteMapping("/users/{id}")
	    public String deleteUser(@PathVariable long id){
	        userService.deleteUserById(id);
	        return "Usuario eliminado con éxito - Ref UserID " + id;
	    }



	    @PreAuthorize("hasRole('ADMINISTRATOR')")
	    @GetMapping("/users")
	    public ResponseEntity<List<User>> allUsers(Pageable pageable) {
	        Page page = userService.allUsers(pageable);
	        return response(page);
	    }

	    private ResponseEntity response(Page page) {

	        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
	        return ResponseEntity.
	                status(httpStatus).
	                header("X-Total-Count", Long.toString(page.getTotalElements()))
	                .header("X-Total-Pages", Long.toString(page.getTotalPages()))
	                .body(page.getContent());
	    }

    @PatchMapping("/users/{id}")
    	public ResponseEntity<?> Partialupdate(@PathVariable("id") long id, @RequestBody  UserDTOtoUpdate updates){

    	try {	
    			return ResponseEntity.status(HttpStatus.OK).body(userService.partialUpdate(id, updates));
    		} catch (Exception e) {
    			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    		}
	    }
}