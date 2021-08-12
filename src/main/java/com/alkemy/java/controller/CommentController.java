package com.alkemy.java.controller;

import com.alkemy.java.dto.CommentDtoCreate;
import com.alkemy.java.model.Comment;
import com.alkemy.java.model.User;
import com.alkemy.java.service.CommentService;
import com.alkemy.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.alkemy.java.dto.CommentDto;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    MessageSource messageSource;

    private static final String ROLE_USER = "[ROLE_USER]";


    @PostMapping("/comments")
    public ResponseEntity<?> newComment(@Valid @RequestBody CommentDtoCreate comment, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errors = new ArrayList<String>();
            result.getAllErrors().stream().forEach((e) -> errors.add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(commentService.createComment(comment));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage("comments.creation.failure", null, null));
            }
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER')")
    @PutMapping("/comments/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto){

        Object obj=SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean isUser=false;
        if (obj.toString().equals(ROLE_USER)){
            if (isUser=validate(id)){
                commentService.updateComment(id,commentDto);
                URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("")
                        .buildAndExpand(id)
                        .toUri();
                return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
            }
            else{
                return new ResponseEntity<>(messageSource.getMessage("member.validate.failure", null, null), HttpStatus.FORBIDDEN);
            }
        }
        else {
            commentService.updateComment(id,commentDto);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("")
                    .buildAndExpand(id)
                    .toUri();
            return new ResponseEntity<>(location,(HttpStatus.ACCEPTED));
        }
    }

    private boolean validate(long id){
        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication2.getName();

        User user =userService.getByEmail(currentPrincipalName);
        Comment comment= commentService.findById(id);

        if (user.getId().equals(comment.getUser().getId())){
            return true;
        }
        else {
            return false;
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
	@GetMapping("/comments")
	public List<CommentDto> allComments()  {
		return commentService.listComments();
	}

    
    @GetMapping("/posts/{id}/comments")
    public List<CommentDto> commentByPost(@PathVariable Long id){
    	return commentService.listByPost(id);
    }


    @PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('USER')")
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<?> DeleteCategory(@PathVariable Long id, HttpServletRequest request){
        try{
            return new ResponseEntity<>(commentService.deleteComent(id,request),HttpStatus.OK);
        }catch(Exception e ){
            return new ResponseEntity<>(messageSource.getMessage("comments.delete.notfound",new Object[] {id},null),HttpStatus.NOT_FOUND);

        }


    }
}
