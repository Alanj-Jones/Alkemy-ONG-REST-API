package com.alkemy.java.mapper;

import com.alkemy.java.dto.request.SlideDto;
import com.alkemy.java.dto.response.SlideCreatedResponseDto;
import com.alkemy.java.dto.response.SlideResponseDto;
import com.alkemy.java.model.Slide;

import org.springframework.stereotype.Component;

@Component
public class SlideMapper {


    public Slide toModelSlides(SlideDto dto, String path) {
        Slide slide = Slide.builder()
                .imageUrl(path)
                .slideOrder(dto.getOrder())
                .text(dto.getText())
                .build();
        return slide;
    }

    public SlideResponseDto toImageResponseDto(Slide slide) {
        SlideResponseDto im = SlideResponseDto.builder()
                .Id(slide.getImageId())
                .organizationId(slide.getOrganizationId())
                .imageUrl(slide.getImageUrl())
                .order(slide.getSlideOrder())
                .text(slide.getText())
                .message("")
                .build();
        return im;
    }

    public SlideCreatedResponseDto toSlidesCreationDto(Slide newSlide, long id) {
        SlideCreatedResponseDto dto = SlideCreatedResponseDto.builder()
                .slidesId(newSlide.getImageId())
                .organizationId(id)
                .imageUrl(newSlide.getImageUrl())
                .order(newSlide.getSlideOrder())
                .text(newSlide.getText())
                .build();
        return dto;
    }

    public SlideResponseDto toUpdateDeletedSlidesResponseDto(Slide slide, String message) {
        SlideResponseDto im = SlideResponseDto.builder()
                .Id(slide.getImageId())
                .organizationId(slide.getOrganizationId())
                .imageUrl(slide.getImageUrl())
                .order(slide.getSlideOrder())
                .text(slide.getText())
                .message(message)
                .build();
        return im;
    }

    public Slide updateSlides(SlideDto dto, Slide slide) {
        slide.setOrganizationId(dto.getOrganizationId());
        slide.setImageUrl(dto.getImageUrl());
        slide.setSlideOrder(dto.getOrder());
        slide.setText(dto.getText());
        return slide;
    }

}
