package com.alkemy.java.mapper;

import com.alkemy.java.dto.TestimonialsDTO;
import com.alkemy.java.dto.response.TestimonialsCreatedResponseDto;
import com.alkemy.java.model.Testimonials;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestimonialsMapper {

    public Testimonials createTestimonials(TestimonialsDTO dto) {
        LocalDateTime updateDateTime = LocalDateTime.now();
        Testimonials test = Testimonials.builder()
                .name(dto.getName())
                .content(dto.getContent())
                .creationDate(updateDateTime)
                .modificationDate(updateDateTime)
                .image(dto.getImage())
                .deleted(false)
                .build();
        return test;
    }

    public TestimonialsCreatedResponseDto createTestimonialsCreatedResponseDto(Testimonials testimonials, String message) {
        TestimonialsCreatedResponseDto test = TestimonialsCreatedResponseDto.builder()
                .id(testimonials.getId())
                .name(testimonials.getName())
                .content(testimonials.getContent())
                .creationDate(String.valueOf(testimonials.getCreationDate()))
                .modificationDate(String.valueOf(testimonials.getModificationDate()))
                .image(testimonials.getImage())
                .deleted(testimonials.isDeleted())
                .message(message)
                .build();
        return test;
    }
}
