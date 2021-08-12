package com.alkemy.java.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDtoFull {

    String name;
    String description;
    String image;
}
