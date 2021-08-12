package com.alkemy.java.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class OrganizationResponseDto {
    private Long id;
    private String name;
    private String  image;
    private String address;
    private String phone;
    private String email;
    private String welcomeText;
    private String aboutUsText;
    private String creationDateTime;
    private String updateDateTime;
    private String message;
}
