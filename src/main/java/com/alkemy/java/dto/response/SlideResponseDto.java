package com.alkemy.java.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SlideResponseDto {
    private Long Id;
    private Long organizationId;
    private String imageUrl;
    private String text;
    private int order;
    private String message;
}
