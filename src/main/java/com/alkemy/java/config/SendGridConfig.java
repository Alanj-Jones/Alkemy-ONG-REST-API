package com.alkemy.java.config;

import com.sendgrid.SendGrid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridConfig {
    
    @Value("${email.api.key}")
    private String appKey;

    @Bean
    public SendGrid getSendGrid() {
        return new SendGrid(appKey);
    }

}
