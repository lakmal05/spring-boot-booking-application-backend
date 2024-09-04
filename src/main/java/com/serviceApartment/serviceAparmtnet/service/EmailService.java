package com.serviceApartment.serviceAparmtnet.service;

import com.serviceApartment.serviceAparmtnet.config.email.SendgridConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final SendgridConfig sendGridConfig;

    @Autowired
    public EmailService(SendgridConfig sendGridConfig) {
        this.sendGridConfig = sendGridConfig;
    }

    public String sendUserCredentials(String email, String message) {
        sendGridConfig.sendEmail(email, message);
        return "Email sent successfully";
    }

}
