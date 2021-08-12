package com.alkemy.java.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class TestimonialsCreatedResponseDto {
    private Long id;
    private String name;
    private String image;
    private String content;
    private boolean deleted;
    private String modificationDate;
    private String creationDate;
    private String message;
}
