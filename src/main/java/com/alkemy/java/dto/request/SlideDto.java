package com.alkemy.java.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@Data
@Getter
@Setter
public class SlideDto {
    private Long organizationId;
    private String imageUrl;
    private String text;
    private int order;
}
