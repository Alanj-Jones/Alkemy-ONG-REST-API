package com.alkemy.java.service.impl;

import com.alkemy.java.dto.request.SlideDto;
import com.alkemy.java.dto.response.SlideCreatedResponseDto;
import com.alkemy.java.dto.response.SlideResponseDto;
import com.alkemy.java.exception.slide.NotDeletedSlideException;
import com.alkemy.java.exception.slide.SlideException;
import com.alkemy.java.exception.slide.NullSlideException;
import com.alkemy.java.mapper.SlideMapper;
import com.alkemy.java.model.Slide;
import com.alkemy.java.repository.ISlideRepository;
import com.alkemy.java.service.ISlideService;
import com.alkemy.java.service.aws.AmazonClient;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SlideServiceImpl implements ISlideService {

    private static final int AMOUNT_SLIDE = 5;

    @Autowired
    AmazonClient amazonClient;
   
    private final ISlideRepository slidesRepository;
    private final SlideMapper slideMapper;

    public Slide findById(Long id) throws NullSlideException {
        return slidesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la slide."));
    }

    public List<Slide> findByIdOrg(Long id) throws IOException{
    	List<Slide> slideList = slidesRepository.findAllByOrgId(id);
    	
    	return slideList;
    }
    
    public SlideResponseDto delete(Long id) {
        Slide slide = search(id);
        if(slide != null){
            slidesRepository.delete(slide);
        }
        Slide aux = search(id);
        if(aux != null){
            SlideException.notDeletedSlidesException(new NotDeletedSlideException());
            return null;
        }
        return slideMapper.toUpdateDeletedSlidesResponseDto(slide, "Slide eliminado correctamente");
    }

    public SlideResponseDto update(Long id, SlideDto dto) {
        Slide slideUpdate = search(id);
        slideMapper.updateSlides(dto, slideUpdate);
        return slideMapper.toUpdateDeletedSlidesResponseDto(slideUpdate, "La Slide ha sido modificado correctamente");
    }
    
    public List<SlideResponseDto> findAllImage() {
        return slidesRepository.findAll().stream()
                .map(u -> slideMapper.toImageResponseDto(u)).collect(Collectors.toList());
    }

    public List<SlideResponseDto> getListSlides() {
        List<SlideResponseDto> listDto = new ArrayList<>();
        List<Slide> slideList = slidesRepository.findAll();
        Collections.shuffle(slideList, new Random());
        List<Slide> slideToShow = slideList.stream().limit(AMOUNT_SLIDE).collect(Collectors.toList());
        slideToShow.stream().forEach(slide -> {
            SlideResponseDto dto = slideMapper.toImageResponseDto(slide);
            listDto.add(dto);
        });
        return listDto;
    }

    @Override
    public SlideCreatedResponseDto save(SlideDto dto, MultipartFile multipart) throws IOException {
        return null;
    }


    @Override
    public Slide newSlide(SlideDto slideDto) throws IOException {
        byte[] files = decode(slideDto.getImageUrl());

        InputStream inputStream = new ByteArrayInputStream(files);
        MultipartFile file = new MockMultipartFile("Newnew.png","Newnew.png", ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

        Slide slide = new Slide();

        String url = amazonClient.uploadFile(file);

        slide.setOrganizationId(slideDto.getOrganizationId());
        if(slideDto.getOrder()!= 0){
        slide.setSlideOrder(slideDto.getOrder());}
        else{slide.setSlideOrder(slidesRepository.max()+1);}
        slide.setText(slideDto.getText());
        slide.setImageUrl(url);
        return slidesRepository.save(slide);
    }

    private Slide search(Long id) {
        Optional<Slide> img = slidesRepository.findById(id);
        if(!img.isPresent()){
            SlideException.notFoundEntityException(new NullSlideException());
            return null;
        }
        return img.get();
    }
    public static byte[] decode(String src){

        return Base64.getDecoder().decode(src);
    }
    

    @Override
	public String deleteSlide(Long id) throws Exception{
		try {
			if(slidesRepository.existsById(id)) {
				slidesRepository.deleteById(id);
				return "Se ha eliminado la imagen correctamente";
			}
			else {
				throw new Exception();
			}
		} catch ( Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
