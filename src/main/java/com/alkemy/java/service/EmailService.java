package com.alkemy.java.service;

import java.io.IOException;

import com.alkemy.java.model.Contacts;

public interface EmailService {

    String sendWelcomeMail(String email, Long id) throws IOException;
    
    void sendContactMail(Contacts contacts);
    
}
