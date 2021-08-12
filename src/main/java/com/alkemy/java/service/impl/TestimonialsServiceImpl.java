package com.alkemy.java.service.impl;

import com.alkemy.java.controller.TestimonialsController;
import com.alkemy.java.dto.TestimonialsDTO;
import com.alkemy.java.dto.response.TestimonialsCreatedResponseDto;
import com.alkemy.java.exception.testimonials.ExistsTestimonialsException;
import com.alkemy.java.exception.testimonials.TestimonialsException;
import com.alkemy.java.mapper.TestimonialsMapper;

import com.alkemy.java.model.Testimonials;
import com.alkemy.java.repository.TestimonialsRepository;
import com.alkemy.java.service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequiredArgsConstructor
@Service
public class TestimonialsServiceImpl implements TestimonialsService{

	private static final int SIZE_URL = 10;

	@Autowired
	TestimonialsRepository repository;

	@Autowired
	MessageSource messageSource;

	@Autowired
	private TestimonialsMapper mapper;

	@Override
	public TestimonialsDTO updateTestimonials(Long id, TestimonialsDTO dto) throws Exception {
		if(repository.existsById(id)) {
			Optional<Testimonials> testimonialOptional = repository.findById(id);
			Testimonials testimonialDB = testimonialOptional.get();
			
			testimonialDB.setImage(dto.getImage());
			testimonialDB.setName(dto.getName());
			testimonialDB.setContent(dto.getContent());
			
			repository.save(testimonialDB);
			return dto;
		}
		else {
		throw new Exception();
		}
	}

	@Override
	public CollectionModel<Testimonials> getUrlsPaginationTestimonials(int numberPage) {
		Pageable pageable = PageRequest.of(numberPage, SIZE_URL);
		Page<Testimonials> page = repository.findAll(pageable);
		List<Link> links = new ArrayList<>();

		if(page.getContent().isEmpty()){ throw new ResponseStatusException(HttpStatus.NO_CONTENT); }
		links.add(linkTo(methodOn(TestimonialsController.class).getUrlsTestimonials(numberPage)).withSelfRel());

		if(page.hasPrevious()){
			links.add(linkTo(methodOn(TestimonialsController.class)
					.getUrlsTestimonials(numberPage - 1)).withRel("prev"));
		}
		if(page.hasNext()){
			links.add(linkTo(methodOn(TestimonialsController.class)
					.getUrlsTestimonials(numberPage + 1)).withRel("next"));
		}
		return CollectionModel.of(page, links);
	}

	@Override
	public void deleteById(Long id) throws Exception{
		try {
			repository.deleteById(id);
		} catch (DataAccessException e) {
			throw new Exception();
		}
	}

    @Override
    public TestimonialsCreatedResponseDto createTestimonialsDto(TestimonialsDTO dto) throws Exception {
		Testimonials testimonials = mapper.createTestimonials(dto);

		if(!notExistTestimonials(dto.getName())){
			repository.save(testimonials);
			return  mapper.createTestimonialsCreatedResponseDto(testimonials,
					messageSource.getMessage("testimonials.creation.success",
							new Object[] {dto.getName()}, null));
		}else{
			TestimonialsException.isExistsTestimonialsException(new ExistsTestimonialsException());
			throw new Exception();
		}
    }

	private boolean notExistTestimonials(String nameTestimonials) {
		Optional<Testimonials> testi = repository.findByName(nameTestimonials);
		if(testi.isPresent()){
			return true;
		}
		return false;
	}
}
