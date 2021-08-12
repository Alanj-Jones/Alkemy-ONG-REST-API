package com.alkemy.java.service.impl;

import java.io.IOException;

import com.alkemy.java.config.DynamicTemplatePersonalization;
import com.alkemy.java.dto.OrganizationDto;
import com.alkemy.java.model.Contacts;
import com.alkemy.java.model.Organization;
import com.alkemy.java.repository.OrganizationRepository;
import com.alkemy.java.service.EmailService;
import com.alkemy.java.util.EmailUtils;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.SendGrid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmailServiceImpl implements EmailService{


	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	SendGrid sendGrid;

    @Autowired
    EmailUtils emailUtils;

    @Autowired
    MessageSource messageSource;

	@Value("${email.api.template}")
    private String template;

	@Value("${email.api.sender}")
	private String emailSender;

	public String sendWelcomeMail(String email, Long id) throws IOException   {
		try {
            OrganizationDto organizationDto = organizationRepository.findOrganizationById(id);
			
            Email from = new Email(emailSender);
			Email to = new Email(email);
			
            Mail mail = new Mail();
			mail.setFrom(from);

            DynamicTemplatePersonalization personalization = emailUtils.personalizeMail(
                organizationDto.getName(),
                organizationDto.getImage(),
                organizationDto.getWelcomeText(),
                messageSource.getMessage("email.set.welcome.subject", new Object[] {organizationDto.getName()}, null),
                organizationDto.getAddress(),
                String.valueOf(organizationDto.getPhone())
            );
            personalization.addTo(to);

			mail.addPersonalization(personalization);
			mail.setTemplateId(template);

            return emailUtils.sendEmail(mail, sendGrid);
	
		} catch (NullPointerException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageSource.getMessage("email.non.existant.organization", new Object[] {id}, null), exc);
	    }

	}
 
    @Override
    public void sendContactMail(Contacts contacts) {
        Organization organization = organizationRepository.findOrgById(contacts.getOrganization().getId());
        
        Email from = new Email(emailSender);
        Email to = new Email(contacts.getEmail());

        Mail mail = new Mail();
        mail.setFrom(from);

        DynamicTemplatePersonalization personalization = emailUtils.personalizeMail(
            organization.getName(),
            organization.getImage(),
            organization.getWelcomeText(),
            messageSource.getMessage("email.set.contact.subject", new Object[] {contacts.getName()}, null),
            organization.getAddress(),
            organization.getPhone()
            );

        personalization.addTo(to);

        mail.addPersonalization(personalization);
        mail.setTemplateId(template);

        emailUtils.sendEmail(mail,sendGrid);
    }    
    
}
