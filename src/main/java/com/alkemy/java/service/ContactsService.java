package com.alkemy.java.service;

import com.alkemy.java.model.Contacts;

import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ContactsService {

    List<Contacts> findContactsList();
    
    ResponseEntity<?> saveContact(Contacts contact);
}
