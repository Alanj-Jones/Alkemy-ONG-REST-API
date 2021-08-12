package com.alkemy.java.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.alkemy.java.model.Contacts;
import com.alkemy.java.service.ContactsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    @PostMapping("/contacts")
    public ResponseEntity<?> newContact(@Valid @RequestBody Contacts contact, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<String>();
            result.getAllErrors().stream().forEach((e) -> errors.add(e.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return contactsService.saveContact(contact);
    }

    // @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/contacts")
    public List<Contacts> listContacts() {
        return contactsService.findContactsList();
    }   
    
}
