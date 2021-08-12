package com.alkemy.java.mapper;

import com.alkemy.java.dto.CommentDto;
import com.alkemy.java.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public static CommentDto toDto(Comment comments) {
        CommentDto dto = new CommentDto();
        dto.setBody(comments.getBody());
        return dto;
    }
}
