package com.alkemy.java.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequestUpdateDto {

    @ApiModelProperty(notes = "${newsModel.updateId}")
    private Long categoriesId;

    @ApiModelProperty(notes = "${newsModel.name}")
    private String name;
    @ApiModelProperty(notes = "${newsModel.content}")
    private String content;
    @ApiModelProperty(notes = "${newsModel.image}")
    private String image;





}