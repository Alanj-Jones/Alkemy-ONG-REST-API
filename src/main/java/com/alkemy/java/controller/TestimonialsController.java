package com.alkemy.java.controller;

import com.alkemy.java.service.TestimonialsService;

import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import com.alkemy.java.dto.TestimonialsDTO;

@RestController
public class TestimonialsController {

	@Autowired
	private TestimonialsService service;

    private static final int SIZE_URL = 10;

	@Autowired
	private MessageSource messageSource;

	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@DeleteMapping("/testimonials/{id}")
	@Operation(summary = "Este endpoint elimina un Testimonio existente")
	public ResponseEntity<?> deleteTestimonials(@PathVariable(name = "id") @ApiParam
			(value = "${testimonio.id}") Long id) {
		try {
			service.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK)
					.body(messageSource.getMessage("testimonials.delete.success", null, null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(messageSource.getMessage("testimonials.update.failure", null, null));
		}
	}

	@ResponseBody
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@PostMapping("/testimonials")
	@Operation(summary = "Este endpoint crea un nuevo Testimonio")
	public ResponseEntity<?> postSaveTestimonials(
			@RequestBody @Valid @ApiParam(value = "${testimonio.body.description}") TestimonialsDTO testimonio) {
		try {
			return ResponseEntity.ok(service.createTestimonialsDto(testimonio));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageSource.getMessage(
					"testimonials.creation.failure", new Object[] { testimonio.getName(), e.getMessage() }, null));
		}
	}

	@ResponseBody
	@PreAuthorize("hasRole('ADMINISTRATOR')")
	@PutMapping("/testimonials/{id}")
	@Operation(summary = "Este endpoint actualiza un testimonio existente")
	public ResponseEntity<?> UpdateTestimonials(@PathVariable @ApiParam
			(value = "${testimonio.id}") Long id,
			@RequestBody(required = false) @ApiParam(value = "${testimonio.body.description}")
	TestimonialsDTO dtoTestimonial) {
		try {
			return ResponseEntity.ok(service.updateTestimonials(id, dtoTestimonial));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(messageSource.getMessage("testimonials.update.failure", null, null));
		}
	}


    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/testimonials")
    public ResponseEntity<?> getUrlsTestimonials(@RequestParam(name = "page",defaultValue = "0") int page) {
        try {
            return ResponseEntity.ok(service.getUrlsPaginationTestimonials(page));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(messageSource.getMessage("testimonials.pagination.failure",
                    null, null));
        }
    }
}

