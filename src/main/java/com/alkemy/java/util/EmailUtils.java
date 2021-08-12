package com.alkemy.java.util;

import java.io.IOException;

import com.alkemy.java.config.DynamicTemplatePersonalization;
import com.alkemy.java.service.impl.EmailServiceImpl;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

    @Autowired
    MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    public String sendEmail(Mail mail, SendGrid sendGrid) {
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());            
            
            Response response = sendGrid.api(request);
            logger.info(response.getBody());

        } catch (IOException e) {
            logger.error( e.getMessage() );
            return messageSource.getMessage("email.send.failure", null, null);
        }
        
        return messageSource.getMessage("email.send.success", null, null);
    }
    

    public DynamicTemplatePersonalization personalizeMail(String name, String image, String welcomeText, String subject, String address, String phone) {
        DynamicTemplatePersonalization personalizedMail = new DynamicTemplatePersonalization();
        personalizedMail.addDynamicTemplateData("name", name);
        personalizedMail.addDynamicTemplateData("image", image);
        personalizedMail.addDynamicTemplateData("welcomeText", welcomeText);
        personalizedMail.addDynamicTemplateData("subject", subject);
        personalizedMail.addDynamicTemplateData("address", address);
        personalizedMail.addDynamicTemplateData("phone", phone);

        return personalizedMail;
    }
    
}
