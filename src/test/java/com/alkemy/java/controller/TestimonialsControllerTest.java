package com.alkemy.java.controller;

import com.alkemy.java.dto.TestimonialsDTO;
import com.alkemy.java.dto.response.TestimonialsCreatedResponseDto;
import com.alkemy.java.mapper.TestimonialsMapper;
import com.alkemy.java.model.Testimonials;
import com.alkemy.java.service.TestimonialsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@WebMvcTest(TestimonialsController.class)
class TestimonialsControllerTest {

    @Configuration
    static class ContextConfiguration{
        @Bean
        public MockMvc mockMvc(){
            return MockMvcBuilders
                    .standaloneSetup(new TestimonialsController())
                    .build();
        }

        @Bean
        public TestimonialsService service(){
            return Mockito.mock(TestimonialsService.class);
        }

        @Bean
        public MessageSource messageSource(){
            return Mockito.mock(MessageSource.class);
        }
    }

    private static final int SIZE_URL = 10;
    private static final int SIZE_COLLECTION = 3;
    private static final int FIRST_PAGE_COLLECTION = 0;
    private static final String BASE_URL = "http://localhost:8082/testimonials";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private TestimonialsService service;

    @Autowired
    private MockMvc mockMvc;

    private TestimonialsMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TestimonialsMapper();
    }

    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    void postSaveTestimonialsIsOk() throws Exception {
        final TestimonialsDTO request = mock(TestimonialsDTO.class);
        request.setName("Testimonials");
        request.setContent("Content Test");
        request.setImage("img");
        String body = request.toString();

        mockMvc.perform(
                post(BASE_URL)
                        .with(user("admin").roles("ADMINISTRATOR"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));

        Testimonials testimonials = mapper.createTestimonials(request);
        TestimonialsCreatedResponseDto response =
                mapper.createTestimonialsCreatedResponseDto(testimonials,"Created ok");
        ResponseEntity responseEntity = new ResponseEntity(response,HttpStatus.CREATED);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
        assertEquals(response.getName(), request.getName());
        assertEquals(response.getContent(), request.getContent());
    }

    @Test
    void postSaveTestimonialsIsFailed() throws Exception {
        final TestimonialsDTO request = mock(TestimonialsDTO.class);
        request.setName("Testimonials");
        request.setContent("");
        request.setImage("img");
        String body = request.toString();

        mockMvc.perform(
                post(BASE_URL)
                        .with(user("admin").roles("ADMINISTRATOR"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));

        String message = (messageSource.getMessage("testimonials.creation.failure",
                new Object[] {request.getName(),new Exception().getMessage()}, null));
        ResponseEntity responseEntity = new ResponseEntity(message,HttpStatus.BAD_REQUEST);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void postSaveTestimonialsIsFailedAuthorization() throws Exception {
        final TestimonialsDTO request = mock(TestimonialsDTO.class);
        request.setName("Testimonials");
        request.setContent("Content Test");
        request.setImage("img");
        String body = request.toString();

        mockMvc.perform(
                post(BASE_URL)
                        .with(user("user").roles("USER"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));

        String message = (messageSource.getMessage("authorization.denied",
                null, null));
        ResponseEntity responseEntity = new ResponseEntity(message ,HttpStatus.UNAUTHORIZED);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void deleteTestimonialsIsOk() throws Exception {
        String url = BASE_URL + "/";
        Long id = 1L;

        mockMvc.perform(
                delete(url)
                        .with(user("admin").roles("ADMINISTRATOR"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(id)));

        service.deleteById(id);
        String message = messageSource.getMessage("testimonials.delete.success",
                null, null);
        ResponseEntity responseEntity = new ResponseEntity(message,HttpStatus.OK);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void deleteTestimonialsIsFailedId() throws Exception {
        String url = BASE_URL + "/";
        Long id = -1L;

        mockMvc.perform(
                delete(url)
                        .with(user("admin").roles("ADMINISTRATOR"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(id)));

        String message = messageSource.getMessage("testimonials.update.failure",
                null, null);
        ResponseEntity responseEntity = new ResponseEntity(message,HttpStatus.NOT_FOUND);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void deleteTestimonialsIsFailedAuthentication() throws Exception {
        String url = BASE_URL + "/";
        Long id = 1L;

        mockMvc.perform(
                delete(url)
                        .with(user("user").roles("USER"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(id)));

        String message = (messageSource.getMessage("authorization.denied",
                null, null));

        ResponseEntity responseEntity = new ResponseEntity(message ,HttpStatus.UNAUTHORIZED);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void updateTestimonialsIsOk() throws Exception {
        final TestimonialsDTO request = mock(TestimonialsDTO.class);
        request.setName("Testimonials");
        request.setContent("Content Test");
        request.setImage("img");
        String body = request.toString();

        String url = BASE_URL + "/" + 1;

        mockMvc.perform(
                put(url)
                        .with(user("admin").roles("ADMINISTRATOR"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));

        TestimonialsDTO response = mock(TestimonialsDTO.class);
        response.setImage(request.getImage());
        response.setContent(request.getContent());
        response.setName(request.getName());

        ResponseEntity responseEntity = new ResponseEntity(response,HttpStatus.OK);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
        assertEquals(response.getContent(), request.getContent());
        assertEquals(response.getName(), request.getName());
    }

    @Test
    void updateTestimonialsIsFailed() throws Exception {
        final TestimonialsDTO request = mock(TestimonialsDTO.class);
        request.setName("Testimonials");
        request.setContent("Content Test");
        request.setImage("img");
        String body = request.toString();

        String url = BASE_URL + "/" + -1;

        mockMvc.perform(
                put(url)
                        .with(user("admin").roles("ADMINISTRATOR"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));

        String message = messageSource.getMessage("testimonials.update.failure",
                null, null);
        ResponseEntity responseEntity = new ResponseEntity(message,HttpStatus.NOT_FOUND);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void updateTestimonialsIsFailedAuthentication() throws Exception {
        final TestimonialsDTO request = mock(TestimonialsDTO.class);
        request.setName("Testimonials");
        request.setContent("Content Test");
        request.setImage("img");
        String body = request.toString();

        String url = BASE_URL + "/" + 1;

        mockMvc.perform(
                put(url)
                        .with(user("user").roles("USER"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));

        String message = (messageSource.getMessage("authorization.denied",
                null, null));

        ResponseEntity responseEntity = new ResponseEntity(message ,HttpStatus.UNAUTHORIZED);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void getUrlsTestimonialsIsOk() throws Exception {
        int numberPage = 1;
        String url = BASE_URL + numberPage;

        mockMvc.perform(
                get(url)
                        .with(user("user").roles("USER"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "page")
                        .param("defaultValue" , "0")
                        .content(String.valueOf(numberPage)));

        Pageable pageable = PageRequest.of(numberPage, SIZE_URL);
        Page<Testimonials> page = new PageImpl<>(new ArrayList<Testimonials>());
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(TestimonialsController.class).getUrlsTestimonials(numberPage)).withSelfRel());

        CollectionModel<Testimonials> collectionModel = new CollectionModel<Testimonials>(page, links);

        ResponseEntity responseEntity =
                new ResponseEntity(collectionModel,HttpStatus.OK);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(collectionModel, responseEntity.getBody());
        assertTrue(numberPage >= FIRST_PAGE_COLLECTION && numberPage < SIZE_COLLECTION);
    }

    @Test
    void getUrlsTestimonialsIsFailed() throws Exception {
        int numberPage = -1;
        String url = BASE_URL + numberPage;

        mockMvc.perform(
                get(url)
                        .with(user("user").roles("USER"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "page")
                        .param("defaultValue" , "0")
                        .content(String.valueOf(numberPage)));

        String message = (messageSource.getMessage("testimonials.pagination.failure",
                null, null));

        ResponseEntity responseEntity =
                new ResponseEntity(message,HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @Test
    void getUrlsTestimonialsIsFailedAuthentication() throws Exception {
        int numberPage = 1;
        String url = BASE_URL + numberPage;

        mockMvc.perform(
                get(url)
                        .with(user("other").roles("SOME"))
                        .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "page")
                        .param("defaultValue" , "0")
                        .content(String.valueOf(numberPage)));

        String message = (messageSource.getMessage("authorization.denied",
                null, null));

        ResponseEntity responseEntity =
                new ResponseEntity(message,HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals(message, responseEntity.getBody());
    }

    @AfterEach
    void tearDown() {
    }

}