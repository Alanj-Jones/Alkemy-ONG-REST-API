package com.alkemy.java.dto;


import lombok.AllArgsConstructor;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesDto {


    private String name;

    private String content;

    long organizationId;

    private String image;
}
