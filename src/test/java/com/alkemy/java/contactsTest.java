package com.alkemy.java;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.alkemy.java.controller.ContactsController;
import com.alkemy.java.model.Contacts;
import com.alkemy.java.repository.ContactsRepository;
import com.alkemy.java.service.ContactsService;
import com.alkemy.java.service.EmailService;
import com.amazonaws.Response;
import com.amazonaws.services.xray.model.Http;

import org.apache.http.client.methods.RequestBuilder;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;

import io.swagger.annotations.Contact;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@WebMvcTest(ContactsController.class)
public class contactsTest {

    @Configuration
    static class ContextConfiguration{
        @Bean
        public MockMvc mockMvc(){
            return MockMvcBuilders
                    .standaloneSetup(new ContactsController())
                    .build();
        }

        @Bean
        public ContactsService service(){
            return Mockito.mock(ContactsService.class);
        }

        @Bean
        public ContactsRepository repository(){
            return Mockito.mock(ContactsRepository.class);
        }

        @Bean
        public MessageSource messageSource(){
            return Mockito.mock(MessageSource.class);
        }
    }

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ContactsService service;

    @Autowired
    private ContactsRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc(){
        assertNotNull(mockMvc);
    }

    @Test
    void postSaveContactTest() throws Exception {

        Contacts request = spy(Contacts.class);
        request.setDeleted(false);
        request.setEmail("Test@test.com");
        request.setId(1L);
        request.setMessage("test msg");
        request.setName("test name");
        request.setOrganization(null);
        request.setPhone("7357");
        String contact = request.toString();

        mockMvc.perform(
            post("/contacts")
            .with(user("admin").roles("ADMINISTRATOR"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(contact)
        );
            
        ResponseEntity<?> response = ResponseEntity.ok().body(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(request, response.getBody());
    }

    @Test
    void getAllContacts() throws Exception {
        List<Contacts> testList = Arrays.asList(new Contacts());
        when(service.findContactsList()).thenReturn(testList);
        
        assertNotNull(service.findContactsList());
        assertFalse(service.findContactsList().isEmpty());


    }
}


