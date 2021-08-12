package com.alkemy.java.dto;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

import org.springframework.context.MessageSource;
import lombok.AllArgsConstructor;

import lombok.Data;

import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDtoCreate {

	

	    @NotNull(message = "El ID del Post es obligatorio.")

	    private Long idNews;

	    private String body;

	    @NotNull(message = "El ID del Usuario es obligatorio.")

	    private Long idUser;
}
