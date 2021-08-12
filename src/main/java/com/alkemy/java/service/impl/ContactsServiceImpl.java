package com.alkemy.java.service.impl;

import java.util.List;

import com.alkemy.java.model.Contacts;
import com.alkemy.java.repository.ContactsRepository;
import com.alkemy.java.service.ContactsService;
import com.alkemy.java.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ContactsServiceImpl implements ContactsService{

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private EmailService mailService;

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseEntity<?> saveContact(Contacts contact) {
        try {
            contactsRepository.save(contact);
            mailService.sendContactMail(contact);
            return new ResponseEntity<>(messageSource.getMessage("contact.creation.success", null, null), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(messageSource.getMessage("contact.creation.failure", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Contacts> findContactsList() {
        return contactsRepository.findAllByOrderByName();
    }
    
}
