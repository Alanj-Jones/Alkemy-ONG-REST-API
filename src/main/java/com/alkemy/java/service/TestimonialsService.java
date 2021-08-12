package com.alkemy.java.service;

import com.alkemy.java.dto.TestimonialsDTO;
import com.alkemy.java.dto.response.TestimonialsCreatedResponseDto;
import com.alkemy.java.model.Testimonials;
import org.springframework.hateoas.CollectionModel;

public interface TestimonialsService {

    void deleteById(Long id) throws Exception;

    TestimonialsDTO updateTestimonials(Long id, TestimonialsDTO dto) throws Exception;

    TestimonialsCreatedResponseDto createTestimonialsDto(TestimonialsDTO dto) throws Exception;

    CollectionModel<Testimonials> getUrlsPaginationTestimonials(int page);
}
