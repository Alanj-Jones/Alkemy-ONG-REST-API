package com.alkemy.java.dto;

import javax.validation.constraints.NotNull;

import com.alkemy.java.dto.response.SlideResponseDto;

import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestimonialsDTO {
	
	
	 @ApiModelProperty( value = "${DtoTestimonio.name}", example = "Testimonio 1", required = true)
	 private String name;
	 @ApiModelProperty(  value = "${DtoTestimonio.image}",example = "Imagendeltestimonio.jpg")
	 private String image;
	 @ApiModelProperty(value = "${DtoTestimonio.content}",  example = "Descripción/Contenido del testimonio")
	 private String content;
}
