package com.alkemy.java.dto.response;


import com.alkemy.java.dto.CategoriesDtoName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseUpdateDto {

    @ApiModelProperty(notes = "${newsModel.name}")
    private String name;

    @ApiModelProperty(notes = "${newsModel.content}")
    private String content;

    @ApiModelProperty(notes = "${newsModel.image}")
    private String image;

    @ApiModelProperty(notes = "${newsModel.categories}")
    private String categories;


}