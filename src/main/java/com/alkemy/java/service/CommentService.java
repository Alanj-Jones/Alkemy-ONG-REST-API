package com.alkemy.java.service;

import com.alkemy.java.dto.CommentDto;
import com.alkemy.java.dto.CommentDtoCreate;
import com.alkemy.java.dto.MemberDto;
import com.alkemy.java.model.Comment;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {

    void updateComment(Long id, CommentDto commentDto);
    Comment findById(Long id);
    List<CommentDto> listComments();

    List<CommentDto> listByPost(Long id);

    ResponseEntity<?> deleteComent(Long id, HttpServletRequest request);
    public String createComment(CommentDtoCreate comment) throws Exception;

}
