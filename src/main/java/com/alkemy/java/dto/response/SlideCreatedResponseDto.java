package com.alkemy.java.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SlideCreatedResponseDto {
    private Long slidesId;
    private Long organizationId;
    private String imageUrl;
    private String text;
    private int order;
}
