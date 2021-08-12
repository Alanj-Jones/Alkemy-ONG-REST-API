package com.alkemy.java.service;

import com.alkemy.java.dto.request.SlideDto;
import com.alkemy.java.dto.response.SlideCreatedResponseDto;
import com.alkemy.java.dto.response.SlideResponseDto;
import com.alkemy.java.exception.slide.NullSlideException;
import com.alkemy.java.model.Slide;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ISlideService {
    public String deleteSlide(Long id) throws Exception;
    Slide findById(Long id) throws NullSlideException;
    SlideResponseDto delete(Long id);
    SlideResponseDto update(Long id, SlideDto dto);
    List<SlideResponseDto> findAllImage();
    List<SlideResponseDto> getListSlides();
    SlideCreatedResponseDto save(SlideDto dto, MultipartFile multipart) throws IOException;
    Slide newSlide (SlideDto slideDto) throws IOException;
    public List<Slide> findByIdOrg(Long id) throws IOException;
}
